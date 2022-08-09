package com.example.spring_3th_assignment.service;

import com.example.spring_3th_assignment.Controller.response.*;
import com.example.spring_3th_assignment.domain.*;
import com.example.spring_3th_assignment.jwt.TokenProvider;
import com.example.spring_3th_assignment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;

    public ResponseDto<?> getAllMyLog(HttpServletRequest request) {
        Member member = validateMember(request);
        System.out.println(member.getId());

        List<Post> postList = postRepository.findAllByMemberId(member.getId());
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<ReComment> reCommentList = reCommentRepository.findByMemberId(member.getId());
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();

        // 멤버가 작성한 게시글 가지고 오기
        for (Post post : postList) {
            postResponseDtoList.add(
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .author(post.getMember().getNickname())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }
        // 멤버가 작성한 댓글 가지고 오기
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .postId(comment.getPost().getId())
                            .commentId(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        // 멤버가 작성한 대댓글 가지고 오기
        for (ReComment reComment : reCommentList) {
            reCommentResponseDtoList.add(
                    ReCommentResponseDto.builder()
                            .commentId(reComment.getComment().getId())
                            .reCommentId(reComment.getId())
                            .author(reComment.getMember().getNickname())
                            .content(reComment.getContent())
                            .createdAt(reComment.getCreatedAt())
                            .modifiedAt(reComment.getModifiedAt())
                            .build()
            );
        }
        // 최종 DTO 만들고 리턴하기
        return ResponseDto.success(
                MyPageResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .postResponseDtoList(postResponseDtoList)
                        .commentResponseDtoList(commentResponseDtoList)
                        .reCommentResponseDtoList(reCommentResponseDtoList)
                        .build()
        );
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}

