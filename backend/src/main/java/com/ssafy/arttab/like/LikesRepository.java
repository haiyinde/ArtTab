package com.ssafy.arttab.like;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "DELETE FROM likes WHERE artwork = :artworkId AND member = :memberId", nativeQuery = true)
    void deleteLike(long artworkId, long memberId);

    @Query(value = "SELECT count(*) FROM likes WHERE artwork=:id", nativeQuery = true)
    int selectLikeNumByArtworkId(Long id);

    @Query(value = "SELECT count(*) FROM likes WHERE artwork=:artwork AND member=:member", nativeQuery = true)
    int selectIsLike(@Param("artwork")Long artwork, @Param("member")Long member);

    @Query(value = "SELECT * FROM likes WHERE member=:id", nativeQuery = true)
    List<Likes> selectByMemberId(Long id);
}
