package com.example.spring_3th_assignment.domain;

import com.example.spring_3th_assignment.Controller.request.ReCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private long likeCount;


    @OneToMany(fetch = LAZY, mappedBy = "reComment", cascade = CascadeType.REMOVE)
    private List<ReCommentLike> reCommentLikeList = new ArrayList<>();

    public void mappingCommentLike(ReCommentLike reCommentLike) {
        this.reCommentLikeList.add(reCommentLike);
    }

    public void updateLikeCount() {
        this.likeCount = (long) this.reCommentLikeList.size();
    }

    public void discountLike(ReCommentLike reCommentLike) {
        this.reCommentLikeList.remove(reCommentLike);

    }


    public void update(ReCommentRequestDto reCommentRequestDto) {
        this.content = reCommentRequestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}