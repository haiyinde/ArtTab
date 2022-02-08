package com.ssafy.arttab.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.artwork.id = :id  ORDER BY c.id DESC")
    List<Comment> findAllDesc(@Param("id") Long id);

}
