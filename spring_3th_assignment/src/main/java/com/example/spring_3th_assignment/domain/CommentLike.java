package com.example.spring_3th_assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "FK_PostLike_Member"))
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_PostLike_Comment"))
    private Comment comment;

    public static boolean bbbb(Optional<CommentLike> optionalCommentLike) {
        return optionalCommentLike.isPresent();
    }

    public void mappingUser(Member member) {
        this.member = member;
        member.mappingCommentLike(this);
    }

    public void mappingComment(Comment comment) {
        this.comment = comment;
        comment.mappingCommentLike(this);
    }


    public CommentLike(Comment comment, Member member) {
        this.comment = comment;
        this.member = member;
    }

}
