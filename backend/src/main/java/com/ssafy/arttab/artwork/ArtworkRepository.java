package com.ssafy.arttab.artwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {


    /***
     *
     * @param title 작품명으로 검색할 키워드
     * @return 작품명으로 검색된 리스트를 반환
     */
    @Query(value = "SELECT * FROM Artwork WHERE title Like %:title%", nativeQuery = true)
    List<Artwork> findAllByTitle(@Param("title")String title);

    /***
     *
     * @param id 작품 리스트를 조회할 회원 번호
     * @return 회원 번호로 조회된 작품 리스트를 반환
     */
    @Query(value = "select * from Artwork where writer = :id", nativeQuery = true)
    List<Artwork> findAllByMemberId(@Param("id") Long id);

    @Query(value = "select count(*) from Artwork where writer = :id", nativeQuery = true)
    int findNumByMemberId(Long id);

    @Query(value = "select top 4 * from Artwork where writer=:writerId order by id desc", nativeQuery = true)
    List<Artwork> find4ByMemberId(@Param("writerId")Long writerId);
}
