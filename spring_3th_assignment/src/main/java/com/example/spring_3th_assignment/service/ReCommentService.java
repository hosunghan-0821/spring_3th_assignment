package com.example.spring_3th_assignment.service;

import com.example.spring_3th_assignment.Controller.request.ReCommentRequestDto;
import com.example.spring_3th_assignment.Controller.response.ReCommentResponseDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.domain.*;
import com.example.spring_3th_assignment.jwt.TokenProvider;
import com.example.spring_3th_assignment.repository.MemberRepository;
import com.example.spring_3th_assignment.repository.ReCommentLikeRepository;
import com.example.spring_3th_assignment.repository.ReCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReCommentService {

    private final ReCommentRepository reCommentRepository;
    private final CommentService commentService;
    private final TokenProvider tokenProvider;
    private final PostService postService;

    private final MemberRepository memberRepository;
    private final ReCommentLikeRepository reCommentLikeRepository;


    // 대댓글 생성
    @Transactional
    public ResponseDto<?> createReComment(ReCommentRequestDto requestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 코멘트 id 입니다.");
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        ReComment recomment = ReComment.builder()
                .member(member)
                .post(post)
                .comment(comment)
                .content(requestDto.getContent())
                .build();
        reCommentRepository.save(recomment);
        return ResponseDto.success(
                ReCommentResponseDto.builder()
                        .commentId(requestDto.getCommentId())
                        .reCommentId(recomment.getId())
                        .author(recomment.getMember().getNickname())
                        .content(recomment.getContent())
                        .createdAt(recomment.getCreatedAt())
                        .modifiedAt(recomment.getModifiedAt())
                        .build()
        );
    }

    // 대댓글 조회 / 댓글 ID 로 조회함
    @Transactional(readOnly = true)
    public ResponseDto<?> getAllReCommentByComment(Long commentId) {
        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        List<ReComment> reCommentList = reCommentRepository.findAllByCommentId(commentId);
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();

        for (ReComment reComment : reCommentList) {
            List<ReCommentLike> reCommentLikeList = reCommentLikeRepository.findByReComment(reComment);
            reCommentResponseDtoList.add(
                    ReCommentResponseDto.builder()
                            .commentId(reComment.getComment().getId())
                            .reCommentId(reComment.getId())
                            .author(reComment.getMember().getNickname())
                            .content(reComment.getContent())
                            .reCommentLike((long) reCommentLikeList.size())
                            .createdAt(reComment.getCreatedAt())
                            .modifiedAt(reComment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(reCommentResponseDtoList);
    }


    // 대댓글 수정
    @Transactional
    public ResponseDto<?> updateReComment(Long id, ReCommentRequestDto reCommentRequestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(id);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        ReComment reComment = isPresentReComment(id);
        if (null == reComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (reComment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        reComment.update(reCommentRequestDto);
        return ResponseDto.success(
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

    // 대댓글 삭제
    @Transactional
    public ResponseDto<?> deleteReComment(Long id, HttpServletRequest request) {
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

        ReComment reComment = isPresentReComment(id);
        if (null == reComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (reComment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        reCommentRepository.delete(reComment);
        return ResponseDto.success("success");
    }


    // 댓글 id 확인?
    @Transactional(readOnly = true)
    public ReComment isPresentReComment(Long id) {
        Optional<ReComment> optionalSubComment = reCommentRepository.findById(id);
        return optionalSubComment.orElse(null);
    }

    //토큰 확인?
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    // 대댓글 좋아요
    public void reCommentLike(Long recommentId, String nickname) {
        ReComment reComment = reCommentRepository.findById(recommentId).orElseThrow(() -> new RuntimeException("aa"));
        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new RuntimeException("aaa"));
        ReCommentLike c = reCommentLikeRepository.findByReCommentAndMember(reComment, member).orElse(null);
        if (c == null) {
            ReCommentLike commentLike = new ReCommentLike(reComment, member);
            reCommentLikeRepository.save(commentLike);
        } else {
            reCommentLikeRepository.delete(c);
        }
    }
}
