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
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String author;
    private Long postLike;
    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
//1