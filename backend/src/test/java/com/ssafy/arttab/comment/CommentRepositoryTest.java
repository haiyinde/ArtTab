package com.ssafy.arttab.comment;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @After
    public void cleanup() {
        commentRepository.deleteAll();
    }

    @Test
    public void 댓글저장() {
        //given
        String content = "이거 좋네요";

        commentRepository.save(Comment.builder()
                .content(content)
                .build());

        //when
        List<Comment> commentList = commentRepository.findAll();

        //then
        Comment comment = commentList.get(0);

        assertThat(comment.getContent()).isEqualTo(content);
    }
}
