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
public class AllPostResponseDto {


    private Long id;
    private String title;
    private String content;
    private String author;
    private String postLikeNum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
