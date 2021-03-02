package com.pratice.boardjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.domain.Comment;
import com.pratice.boardjpa.domain.Post;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void 댓글을_등록한다() throws Exception {
        // given
        Board board = createBoard();
        Post post = createPost(board);
        Comment comment = Comment.createComment(post, "댓글1", 'Y');

        // when
        commentRepository.save(comment);
        Comment findComment = commentRepository.findCommentById(comment.getId());

        // then
        assertThat(findComment).isEqualTo(comment);
        assertThat(findComment.getPost()).isEqualTo(post);
        assertThat(findComment.getPost().getBoard()).isEqualTo(board);
    }

    @Test
    public void 대댓글을_등록한다() throws Exception {
        // given
        Board board = createBoard();
        Post post = createPost(board);
        Comment comment = Comment.createComment(post, "댓글1", 'Y');
        em.persist(comment);

        Comment comment2 = Comment.createReplyComment(post, comment, "댓글2", 'Y');

        // when
        commentRepository.save(comment2);
        Comment findReplyComment = commentRepository.findCommentById(comment2.getId());
        // then
        assertThat(findReplyComment).isEqualTo(comment2);
        assertThat(findReplyComment.getParent()).isEqualTo(comment);
        assertThat(findReplyComment.getPost()).isEqualTo(post);
        assertThat(findReplyComment.getPost().getBoard()).isEqualTo(board);
    }


    private Post createPost(Board board) {
        return Post.createPost("제목", "내용", 'Y', board);
    }

    private Board createBoard() {
        Board board = new Board("title", 'Y');
        em.persist(board);
        return board;
    }

}