package com.ssafy.arttab.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    /***
     *
     * @param follower 팔로우 취소하는 회원
     * @param followee 팔로우 취소할 작가
     */
    @Modifying
    @Query(value = "DELETE FROM follow WHERE follower = :follower AND followee = :followee", nativeQuery = true)
    void unFollow(long follower, long followee);

    /***
     *
     * @param id 현재 팔로워 수를 조회할 회원번호
     * @return 팔로워 수를 리턴합니다
     */
    @Query(value = "SELECT count(*) FROM Follow WHERE follower = :id", nativeQuery = true)
    int findAllFollowCnt(Long id);

}
