package com.pratice.boardjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.domain.Comment;
import com.pratice.boardjpa.domain.Post;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
        Comment comment = Comment.createComment(post, "댓글1", true);

        // when
        commentRepository.save(comment);
        Comment findComment = commentRepository.findById(comment.getId());

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
        Comment comment = Comment.createComment(post, "댓글1", true);
        em.persist(comment);

        Comment comment2 = Comment.createReplyComment(post, comment, "댓글2", true);

        // when
        commentRepository.save(comment2);
        Comment findReplyComment = commentRepository.findById(comment2.getId());
        // then
        assertThat(findReplyComment).isEqualTo(comment2);
        assertThat(findReplyComment.getParent()).isEqualTo(comment);
        assertThat(findReplyComment.getPost()).isEqualTo(post);
        assertThat(findReplyComment.getPost().getBoard()).isEqualTo(board);
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    @Rollback(false)
    public void deleteComment() throws Exception {
        // given
        Board board = createBoard();
        Post post = createPost(board);
        Comment comment = Comment.createComment(post, "댓글1", true);

        for (int i = 0; i < 10; i++) {
            Comment replyComment = Comment.createReplyComment(post, comment, "내내용", true);
            comment.getChild().add(replyComment);
        }

        em.persist(comment);

        // when
        commentRepository.delete(comment);
        Comment deletedComment = commentRepository.findById(comment.getId());

        // then
        assertThat(deletedComment).isNull();
        assertThat(comment.getChild()).isEmpty();
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