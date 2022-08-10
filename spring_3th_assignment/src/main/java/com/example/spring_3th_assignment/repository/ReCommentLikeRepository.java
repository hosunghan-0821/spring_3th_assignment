package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Member;
import com.example.spring_3th_assignment.domain.ReComment;
import com.example.spring_3th_assignment.domain.ReCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReCommentLikeRepository extends JpaRepository<ReCommentLike, Long> {
    Optional<ReCommentLike> findByReCommentAndMember(ReComment reComment, Member member);

//    List<ReCommentLike> findByReComment(ReComment reComment);
}
