package com.example.spring_3th_assignment.Controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {
    private Long id;
    private String nickname;
    private List<PostResponseDto> postList;
    private List<CommentResponseDto> commentList;
    private List<ReCommentResponseDto> reCommentList;
    private List<PostLikeResponseDto> postLikeList;
    private List<CommentLikeResponseDto> commentLikeList;
}
