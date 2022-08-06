package com.example.spring_3th_assignment.service;


import com.example.spring_3th_assignment.Controller.request.SubCommentRequestDto;
import com.example.spring_3th_assignment.Controller.response.CommentResponseDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.Controller.response.SubCommentResponseDto;
import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.Member;
import com.example.spring_3th_assignment.domain.Post;
import com.example.spring_3th_assignment.domain.SubComment;
import com.example.spring_3th_assignment.jwt.TokenProvider;
import com.example.spring_3th_assignment.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;
    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public ResponseDto<?> createSubComment(SubCommentRequestDto subCommentRequestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(subCommentRequestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }
        Post post = postService.isPresentPost(subCommentRequestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        SubComment subComment = SubComment.builder()
                .member(member)
                .post(post)
                .comment(comment)
                .content(subCommentRequestDto.getContent())
                .build();
        subCommentRepository.save(subComment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(subCommentRequestDto.getCommentId())
                        .author(subComment.getMember().getNickname())
                        .content(subComment.getContent())
                        .createdAt(subComment.getCreatedAt())
                        .modifiedAt(subComment.getModifiedAt())
                        .build()
        );
    }

//    @Transactional(readOnly = true)
//    public ResponseDto<?> getAllCommentsByPost(Long postId) {
//        Post post = postService.isPresentPost(postId);
//        if (null == post) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//        }
//
//        List<Comment> commentList = commentRepository.findAllByPost(post);
//        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//
//        for (Comment comment : commentList) {
//            commentResponseDtoList.add(
//                    CommentResponseDto.builder()
//                            .id(comment.getId())
//                            .author(comment.getMember().getNickname())
//                            .content(comment.getContent())
//                            .createdAt(comment.getCreatedAt())
//                            .modifiedAt(comment.getModifiedAt())
//                            .build()
//            );
//        }
//        return ResponseDto.success(commentResponseDtoList);
//    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllSubCommentsByComment(Long commentId) {
        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

        for (SubComment subComment : subCommentList) {
            subCommentResponseDtoList.add(
                    SubCommentResponseDto.builder()
                            .id(subComment.getId())
                            .author(subComment.getMember().getNickname())
                            .content(subComment.getContent())
                            .createdAt(subComment.getCreatedAt())
                            .modifiedAt(subComment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(subCommentResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> updateSubComment(Long id, SubCommentRequestDto subCommentRequestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

//        Post post = postService.isPresentPost(subCommentRequestDto.getPostId());
//        if (null == post) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//        }

        Comment comment = commentService.isPresentComment(id);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        SubComment subComment = isPresentSubComment(id);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (subComment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        subComment.update(subCommentRequestDto);
        return ResponseDto.success(
                SubCommentResponseDto.builder()
                        .id(subComment.getId())
                        .author(subComment.getMember().getNickname())
                        .content(subComment.getContent())
                        .createdAt(subComment.getCreatedAt())
                        .modifiedAt(subComment.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deleteSubComment(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        SubComment subComment = isPresentSubComment(id);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (subComment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        subCommentRepository.delete(subComment);
        return ResponseDto.success("success");
    }

    @Transactional(readOnly = true)
    public SubComment isPresentSubComment(Long id) {
        Optional<SubComment> optionalSubComment = subCommentRepository.findById(id);
        return optionalSubComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}