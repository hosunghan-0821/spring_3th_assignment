package com.example.spring_3th_assignment.Controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long postId;
    private Long commentId;
    private String author;
    private String content;
    private Long commentLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ReCommentResponseDto> reCommentResponseDtoList;
}
