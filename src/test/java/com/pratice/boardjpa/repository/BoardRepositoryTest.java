package com.pratice.boardjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pratice.boardjpa.domain.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 게시판을_생성한다() throws Exception {
        // given
        final Board board = Board.createBoard("title1", true);
        // when
        boardRepository.save(board);
        Board findBoard = boardRepository.findById(board.getId());
        // then
        assertThat(findBoard).isEqualTo(board);
    }

}