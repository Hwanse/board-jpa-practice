package com.pratice.boardjpa.repository;

import com.pratice.boardjpa.domain.Post;
import java.util.List;
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

    public void delete(Post post) {
        em.remove(post);
        post.deleteChilds();
    }

    public void deleteBulkOperation(Post post) {
        em.createQuery("delete from Post p " +
                " where p.parent = :postId" +
                " and p.id in (select c.post from Comment c" +
                " where c.post = :postId )"
            ).executeUpdate();

    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                 .getResultList();
    }
}
