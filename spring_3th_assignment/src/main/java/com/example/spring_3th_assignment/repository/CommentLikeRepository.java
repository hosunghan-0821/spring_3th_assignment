package com.example.spring_3th_assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.CommentLike;
import com.example.spring_3th_assignment.domain.Member;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);
    List<CommentLike> findByComment(Comment comment);
    List<CommentLike> findByMemberId(Long member_id);

}
