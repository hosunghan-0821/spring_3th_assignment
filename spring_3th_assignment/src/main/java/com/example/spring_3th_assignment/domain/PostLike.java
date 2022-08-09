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
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "FK_PostLike_User"))
    private Member member;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_PostLike_Post"))
    private Post post;

    public static boolean isVotedPost(Optional<PostLike> optionalPostLike) {
        return optionalPostLike.isPresent();
    }


    public void mappingUser(Member member) {
        this.member = member;
        member.mappingPostLike(this);
    }

    public void mappingPost(Post post) {
        this.post = post;
        post.mappingPostLike(this);
    }



    public PostLike(Post post, Member member){
        this.post = post;
        this.member = member;
    }


}