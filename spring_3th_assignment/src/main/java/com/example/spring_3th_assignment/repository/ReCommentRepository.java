package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
    List<ReComment> findByMemberId(Long member_id);
    List<ReComment> findAllByCommentId(Long comment_id);
    List<ReComment> findAllByComment(Comment comment);



}
