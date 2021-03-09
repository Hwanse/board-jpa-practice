package com.pratice.boardjpa.repository;

import com.pratice.boardjpa.domain.Board;
import java.util.List;
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

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                 .getResultList();
    }

    public void delete(Board board) {
        em.remove(board);
        board.deletePosts();
    }

}
