package com.pratice.boardjpa.repository;

import com.pratice.boardjpa.domain.Comment;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findCommentById(Long id) {
        return em.find(Comment.class, id);
    }

}