package com.pratice.boardjpa.repository;

import com.pratice.boardjpa.domain.Board;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findBoardById(Long id) {
        return em.find(Board.class, id);
    }

}
