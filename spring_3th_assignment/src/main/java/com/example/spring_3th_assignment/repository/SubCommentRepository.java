package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);
}
