package com.example.spring_3th_assignment.domain;


import com.example.spring_3th_assignment.Controller.request.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

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


  @Column(nullable = true)
  private Long likeCount;


  @OneToMany(fetch = LAZY, mappedBy = "comment", cascade = CascadeType.REMOVE)
  private List<CommentLike> commentLikeList = new ArrayList<>();

  public void mappingCommentLike(CommentLike commentLike) {
    this.commentLikeList.add(commentLike);
  }

  public void updateLikeCount() {
    this.likeCount = (long) this.commentLikeList.size();
  }

  public void discountLike(CommentLike commentLike) {
    this.commentLikeList.remove(commentLike);

  }


    public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }
}
