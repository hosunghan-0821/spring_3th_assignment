package com.example.spring_3th_assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private ReComment reComment;

    public static boolean bbbb(Optional<ReCommentLike> optionalCommentLike) {
        return optionalCommentLike.isPresent();
    }

    public void mappingUser(Member member) {
        this.member = member;
        member.mappingReCommentLike(this);
    }

//    public void mappingComment(ReComment reComment) {
//        this.reComment = reComment;
//        reComment.mappingReCommentLike(this);
//    }


    public ReCommentLike(ReComment reComment, Member member) {
        this.reComment = reComment;
        this.member = member;
    }

}