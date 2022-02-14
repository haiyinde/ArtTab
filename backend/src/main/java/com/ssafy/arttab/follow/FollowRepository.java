package com.ssafy.arttab.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    /**
     *
     * @param follower
     * @param followee
     * @return 값이 존재하는지 확인하여 존재하면 true 없으면 false 반환
    */
    @Query(value = "select count(*) > 0 from follow where follower = :follower and followee = :followee", nativeQuery = true)
    Optional<Long> checkFollow(@Param(value="follower") long follower, @Param(value="followee") long followee);

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
    @Query(value = "SELECT count(*) FROM follow WHERE follower = :id", nativeQuery = true)
    int findAllFollowCnt(Long id);

    /***
     *
     * @param id 현재 follower 수를 조회할 회원번호
     * @return id가 팔로우하는 회원의 수를 리턴합니다
     */
    @Query(value = "SELECT count(*) FROM follow WHERE follower = :id", nativeQuery = true)
    int findAllFollowingCnt(Long id);

    /***
     *
     * @param id 현재 followee 수를 조회할 회원번호
     * @return id를 팔로우하는 회원의 수를 리턴합니다
     */
    @Query(value = "SELECT count(*) FROM follow WHERE followee = :id", nativeQuery = true)
    int findAllFollowedCnt(Long id);

    /***
     *
     * @param follower
     * @param followee
     * @return follower가 followee를 팔로우하는지 리턴
     */
    @Query(value = "SELECT count(*) FROM follow WHERE follower = :follower AND followee = :followee", nativeQuery = true)
    int isFollow(Long follower, Long followee);

    /***
     *
     * @param id 현재 팔로잉하고 있는 사람을 알고 싶은 회원번호
     * @return id가 팔로우하는 회원의 리스트를 리턴합니다
     */
    @Query(value = "SELECT * FROM follow WHERE follower = :id", nativeQuery = true)
    List<Follow> findAllFollowing(@Param("id") Long id);
}
