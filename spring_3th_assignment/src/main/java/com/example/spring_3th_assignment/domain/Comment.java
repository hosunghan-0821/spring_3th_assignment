package com.example.spring_3th_assignment.domain;


import com.example.spring_3th_assignment.Controller.request.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  private ReComment reComment;

  @Column(nullable = false)
  private String content;

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }
}
