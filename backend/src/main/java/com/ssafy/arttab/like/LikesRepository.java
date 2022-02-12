package com.ssafy.arttab.like;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "DELETE FROM Likes WHERE artwork = :artworkId AND member = :memberId", nativeQuery = true)
    void deleteLike(long artworkId, long memberId);
}
