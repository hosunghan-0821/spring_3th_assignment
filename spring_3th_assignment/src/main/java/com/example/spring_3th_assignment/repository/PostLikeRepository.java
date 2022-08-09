package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.Member;
import com.example.spring_3th_assignment.domain.Post;
import com.example.spring_3th_assignment.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findAllByPostId(Long post_id);

    Optional<PostLike> findByPostAndMember(Post post, Member member);

    List<PostLike> findByPost(Post id);





}
