package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Member;
import com.example.spring_3th_assignment.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}
