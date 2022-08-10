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
public class ReCommentLikeResponseDto {
    private Long commentId;
    private Long reCommentId;
    private String author;
    private String content;
    private Long reCommentLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}