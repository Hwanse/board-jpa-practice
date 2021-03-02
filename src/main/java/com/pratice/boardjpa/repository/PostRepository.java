package com.pratice.boardjpa.repository;

import com.pratice.boardjpa.domain.Post;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findPostById(Long id) {
        return em.find(Post.class, id);
    }

}
