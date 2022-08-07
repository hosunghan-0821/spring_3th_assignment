package com.example.spring_3th_assignment.domain;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postImage")
public class Image extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String imgURL;



    public Image(Post post, String imageUrl){
        this.post=post;
        this.imgURL = imageUrl;
    }
}
