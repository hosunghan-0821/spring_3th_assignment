package com.example.spring_3th_assignment.Controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResponseDto {
    private Long postId;
    private Long commentId;
    private String author;
    private String content;
    private Long commentLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
