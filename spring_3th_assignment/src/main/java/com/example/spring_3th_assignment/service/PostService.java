package com.example.spring_3th_assignment.service;


import com.example.spring_3th_assignment.Controller.request.PostRequestDto;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;


    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
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

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .build();
        postRepository.save(post);
        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getNickname())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<PostLike> postLikeList = postLikeRepository.findAllByPostId(post.getId());
        Long likeCount = (long) postLikeList.size();

        for (Comment comment : commentList) {
            List<CommentLike> commentLikeList = commentLikeRepository.findByComment(comment);
            List<ReComment> reCommentList = reCommentRepository.findAllByComment(comment);
            List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();
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

            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .postId(comment.getPost().getId())
                            .commentId(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .commentLike((long) commentLikeList.size()) // 호성님이 만든부분 이태민 수정함
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .reCommentResponseDtoList(reCommentResponseDtoList)
                            .build()
            );
        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .postLike(likeCount)
                        .commentResponseDtoList(commentResponseDtoList)
                        .author(post.getMember().getNickname())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }


    public ResponseDto<?> getAllPost() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            Member member = memberRepository.findById(post.getMember().getId()).orElse(null);
            List<PostLike> postLikeList = postLikeRepository.findByPost(post);

            AllPostResponseDto allPostResponseDto = AllPostResponseDto.builder()
                    .content(post.getContent())
                    .id(post.getId())
                    .author(member.getNickname())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .title(post.getTitle())
                    .postLikeNum(Integer.toString(postLikeList.size()))
                    .build();
            allPostResponseDtoList.add(allPostResponseDto);
        }
        return ResponseDto.success(allPostResponseDtoList);
    }

    @Transactional
    public ResponseDto<Post> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
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

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
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

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
        return ResponseDto.success("delete success");
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }


    public void postLike(Long postId, String nickname) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("aa"));
        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new RuntimeException("aaa"));
        PostLike a = postLikeRepository.findByPostAndMember(post, member).orElse(null);
        if (a == null) {
            PostLike postLike = new PostLike(post, member);
            postLikeRepository.save(postLike);
        } else {
            postLikeRepository.delete(a);
        }
    }
}
