package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);

}
