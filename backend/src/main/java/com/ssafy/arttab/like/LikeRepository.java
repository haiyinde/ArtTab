package com.ssafy.arttab.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "SELECT count(*) FROM Likes WHERE artwork=:id", nativeQuery = true)
    int selectLikeNumByArtworkId(Long id);

    @Query(value = "SELECT count(*) FROM Likes WHERE artwork=:artwork AND member=:member", nativeQuery = true)
    int selectIsLike(@Param("artwork")Long artwork, @Param("member")Long member);

}
