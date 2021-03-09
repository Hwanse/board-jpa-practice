package com.pratice.boardjpa.service;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public Long createBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    public void deleteBoard(Long boardId) {
        Board beDeleteBoard = boardRepository.findById(boardId);
        boardRepository.delete(beDeleteBoard);
    }

}
