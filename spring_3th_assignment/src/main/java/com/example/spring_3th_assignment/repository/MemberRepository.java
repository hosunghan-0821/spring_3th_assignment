package com.example.spring_3th_assignment.repository;


//import com.example.spring_3th_assignment.Controller.response.MyPageResponseDto;
import com.example.spring_3th_assignment.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findById(Long id);
  Optional<Member> findByNickname(String nickname);


//  MyPageResponseDto findAllById(Long memberId);
}
