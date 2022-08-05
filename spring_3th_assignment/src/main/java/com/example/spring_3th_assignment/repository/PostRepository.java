package com.example.spring_3th_assignment.repository;


import com.example.spring_3th_assignment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByOrderByModifiedAtDesc();
}
