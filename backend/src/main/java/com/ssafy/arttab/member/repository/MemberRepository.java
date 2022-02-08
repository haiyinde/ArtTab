package com.ssafy.arttab.member.repository;


import com.ssafy.arttab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    //닉네임 중복 검사
    boolean existsMembersByNickname(String nickname);

    // 이메일 중복 검사
    boolean existsMembersByEmail(String email);

    // 이메일로 회원정보 받기
    Member findMemberByEmail(String email);


    Member findMemberByNickname(String nickname);
//    List<GalleryItem> findGalleryItemById(int id);

    /***
     *
     * @param nickname 회원 목록을 조회할 닉네임
     * @return 닉네임으로 조회한 회원 리스트 반환
     */
    @Query(value = "SELECT * FROM member WHERE nickname LIKE %:nickname%", nativeQuery = true)
    List<Member> findAllByNickname(@Param("nickname") String nickname);
}
