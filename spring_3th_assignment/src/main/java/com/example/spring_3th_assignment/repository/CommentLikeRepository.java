package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);
    List<CommentLike> findByComment(Comment comment);
    List<CommentLike> findByMemberId(Long member_id);

}
