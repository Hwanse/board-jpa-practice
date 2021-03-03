package com.pratice.boardjpa.repository;

import com.pratice.boardjpa.domain.Comment;
import java.util.List;
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

    public void delete(Comment comment) {
        em.remove(comment);
        comment.deleteChild();
    }

    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                 .getResultList();
    }

}