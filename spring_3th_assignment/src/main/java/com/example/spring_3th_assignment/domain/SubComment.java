package com.example.spring_3th_assignment.domain;

import com.example.spring_3th_assignment.Controller.request.CommentRequestDto;
import com.example.spring_3th_assignment.Controller.request.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

//    @OneToMany(mappedBy = "comment")
//    private List<Comment> subComment = new ArrayList<>();



    public void update(SubCommentRequestDto subCommentRequestDto) {
        this.content = subCommentRequestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}

//@Builder
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class Comment extends Timestamped {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String content;
//
//    @JoinColumn(name = "member_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;
//
//    @JoinColumn(name = "post_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Post post;
//
////  @JoinColumn(name = "comment_id")
////  @ManyToOne(fetch = FetchType.LAZY)
////  private Comment comment;
////
////  @OneToMany(mappedBy = "comment")
////  private List<Comment> subComment = new ArrayList<>();
//
//
//
//    public void update(CommentRequestDto commentRequestDto) {
//        this.content = commentRequestDto.getContent();
//    }
//
//    public boolean validateMember(Member member) {
//        return !this.member.equals(member);
//    }
//}