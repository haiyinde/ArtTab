package com.ssafy.arttab.member.repository;


import com.ssafy.arttab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    //닉네임 중복 검사
    boolean existsMembersByNickname(String nickname);

    // 이메일 중복 검사
    boolean existsMembersByEmail(String email);

    // 이메일로 회원정보 받기
    Member findMemberByEmail(String email);

}
