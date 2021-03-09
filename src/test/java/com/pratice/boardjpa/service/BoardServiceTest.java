package com.pratice.boardjpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    public void test() throws Exception {
        // given
        boardService = new BoardService(boardRepository);
        Board board = createBoard("내용", true);

        // when
        given(boardService.createBoard(board)).willReturn(1L);

    }

    private Board createBoard(String title, boolean flag) {
        return Board.createBoard(title, flag);
    }

}