package com.pratice.boardjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.domain.Post;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void 게시글을_생성한다() throws Exception {
        // given
        Board board = new Board("title", 'Y');
        em.persist(board);

        Post post = Post.createPost("제목", "내용", 'Y', board);
        // when
        postRepository.save(post);
        Post findPost = postRepository.findPostById(post.getId());

        // then
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    @Rollback(false)
    public void 답변게시글을_생성한다() throws Exception {
        // given
        Board board = new Board("title", 'Y');
        em.persist(board);

        Post post = Post.createPost("제목", "내용", 'Y', board);
        em.persist(post);

        Post replyPost = Post.createReplyPost("제목2", "내용", 'Y', board, post);

        // when
        postRepository.save(replyPost);
        Post findReplyPost = postRepository.findPostById(replyPost.getId());
        // then
        assertThat(findReplyPost).isEqualTo(replyPost);
        assertThat(findReplyPost.getParent()).isEqualTo(post);
    }


}