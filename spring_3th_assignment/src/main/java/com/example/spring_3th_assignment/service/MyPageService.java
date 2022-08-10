package com.example.spring_3th_assignment.service;

import com.example.spring_3th_assignment.Controller.response.*;
import com.example.spring_3th_assignment.domain.*;
import com.example.spring_3th_assignment.jwt.TokenProvider;
import com.example.spring_3th_assignment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReCommentLikeRepository reCommentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;

    public ResponseDto<?> getAllMyLog(HttpServletRequest request) {
        Member member = validateMember(request);
        System.out.println(member.getId());

        // 작성한 게시글,댓글,대댓글 찾기
        List<Post> postList = postRepository.findAllByMemberId(member.getId());
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<ReComment> reCommentList = reCommentRepository.findByMemberId(member.getId());
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();

        // 좋아요한 게시글,댓글,대댓글 찾기
        List<PostLike> postLikeList = postLikeRepository.findByMemberId(member.getId());
        List<PostLikeResponseDto> postLikeResponseDtoList = new ArrayList<>();
        List<CommentLike> commentLikeList = commentLikeRepository.findByMemberId(member.getId());
        List<CommentLikeResponseDto> commentLikeResponseDtoList = new ArrayList<>();
        List<ReCommentLike> reCommentLikeList = reCommentLikeRepository.findByMemberId(member.getId());
        List<ReCommentLikeResponseDto> reCommentLikeResponseDtoList = new ArrayList<>();

        // 멤버가 작성한 게시글 가지고 오기
        for (Post post : postList) {
            List<PostLike> postLikeList1 = postLikeRepository.findByPost(post);
            postResponseDtoList.add(
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .author(post.getMember().getNickname())
                            .postLike((long) postLikeList1.size())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }

        // 멤버가 작성한 댓글 가지고 오기
        for (Comment comment : commentList) {
            List<CommentLike> commentLikeList1 = commentLikeRepository.findByComment(comment);
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .postId(comment.getPost().getId())
                            .commentId(comment.getId())
                            .author(comment.getMember().getNickname())
                            .commentLike((long) commentLikeList1.size())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        // 멤버가 작성한 대댓글 가지고 오기
        for (ReComment reComment : reCommentList) {
            List<ReCommentLike> reCommentLikeList1 = reCommentLikeRepository.findByReComment(reComment);
            reCommentResponseDtoList.add(
                    ReCommentResponseDto.builder()
                            .commentId(reComment.getComment().getId())
                            .reCommentId(reComment.getId())
                            .author(reComment.getMember().getNickname())
                            .content(reComment.getContent())
                            .reCommentLike((long) reCommentLikeList1.size())
                            .createdAt(reComment.getCreatedAt())
                            .modifiedAt(reComment.getModifiedAt())
                            .build()
            );
        }
        // 멤버가 좋아요한 게시글 가지고 오기
        for (PostLike postLike : postLikeList) {
            List<PostLike> postLikeList1 = postLikeRepository.findByPost(postLike.getPost());
            postLikeResponseDtoList.add(
                    PostLikeResponseDto.builder()
                            .id(postLike.getPost().getId())
                            .title(postLike.getPost().getTitle())
                            .author(postLike.getMember().getNickname())
                            .postLike((long) postLikeList1.size())
                            .content(postLike.getPost().getContent())
                            .createdAt(postLike.getPost().getCreatedAt())
                            .modifiedAt(postLike.getPost().getModifiedAt())
                            .build()
            );
        }

        // 멤버가 좋아요한 댓글 가지고 오기
        for (CommentLike commentLike : commentLikeList) {
            List<CommentLike> commentLikeList1 = commentLikeRepository.findByComment(commentLike.getComment());
            commentLikeResponseDtoList.add(
                    CommentLikeResponseDto.builder()
                            .postId(commentLike.getComment().getPost().getId())
                            .commentId(commentLike.getComment().getId())
                            .author(commentLike.getMember().getNickname())
                            .commentLike((long) commentLikeList1.size())
                            .content(commentLike.getComment().getContent())
                            .createdAt(commentLike.getComment().getCreatedAt())
                            .modifiedAt(commentLike.getComment().getModifiedAt())
                            .build()
            );
        }

        // 멤버가 좋아요한 대댓글 가지고 오기
        for (ReCommentLike reCommentLike : reCommentLikeList) {
            List<ReCommentLike> reCommentLikeList1 = reCommentLikeRepository.findByReComment(reCommentLike.getReComment());
            reCommentLikeResponseDtoList.add(
                    ReCommentLikeResponseDto.builder()
                            .commentId(reCommentLike.getReComment().getComment().getId())
                            .reCommentId(reCommentLike.getReComment().getId())
                            .author(reCommentLike.getMember().getNickname())
                            .content(reCommentLike.getReComment().getContent())
                            .reCommentLike((long) reCommentLikeList1.size())
                            .createdAt(reCommentLike.getReComment().getCreatedAt())
                            .modifiedAt(reCommentLike.getReComment().getModifiedAt())
                            .build()
            );
        }

        // 최종 DTO 만들고 리턴하기
        return ResponseDto.success(
                MyPageResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .postList(postResponseDtoList)
                        .commentList(commentResponseDtoList)
                        .reCommentList(reCommentResponseDtoList)
                        .postLikeList(postLikeResponseDtoList)
                        .commentLikeList(commentLikeResponseDtoList)
                        .reCommentLikeList(reCommentLikeResponseDtoList)
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

