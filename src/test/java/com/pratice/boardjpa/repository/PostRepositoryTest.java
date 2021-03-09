package com.pratice.boardjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.domain.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("게시글을 생성한다")
    public void createPost() throws Exception {
        // given
        Board board = createBoard();

        Post post = createPost(board);
        // when
        postRepository.save(post);
        Post findPost = postRepository.findById(post.getId());

        // then
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    @DisplayName("답변게시글을 생성한다")
    public void createReplyPost() throws Exception {
        // given
        Board board = createBoard();

        Post post = createPost(board);
        em.persist(post);

        Post replyPost = Post.createReplyPost("제목2", "내용", true, board, post);

        // when
        postRepository.save(replyPost);
        Post findReplyPost = postRepository.findById(replyPost.getId());
        // then
        assertThat(findReplyPost).isEqualTo(replyPost);
        assertThat(findReplyPost.getParent()).isEqualTo(post);
    }

    @Test
    @DisplayName("게시글을 삭제한다")
    @Rollback(false)
    public void deletePost() throws Exception {
        // given
        Board board = createBoard();
        Post post = createPost(board);

        for (int i = 0; i < 10; i++) {
            Post replyPost = Post.createReplyPost("title", "내용",
                                                  true, board, post);
        }
        em.persist(post);

        // when
        postRepository.delete(post);
        Post deletedPost = postRepository.findById(post.getId());

        // then
        assertThat(deletedPost).isNull();
        assertThat(post.getChild()).isEmpty();
    }

    private Post createPost(Board board) {
        return Post.createPost("제목", "내용", true, board);
    }

    private Board createBoard() {
        Board board = Board.createBoard("title", true);
        em.persist(board);
        return board;
    }

}