package com.example.spring_3th_assignment.Controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentRequestDto {
    private Long postId;
    private Long commentId;
    private String content;
}