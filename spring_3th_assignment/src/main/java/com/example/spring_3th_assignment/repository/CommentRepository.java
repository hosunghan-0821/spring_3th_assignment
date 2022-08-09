package com.example.spring_3th_assignment.repository;


import com.example.spring_3th_assignment.domain.Comment;
import com.example.spring_3th_assignment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findAllByMemberId(Long member_id);

}
