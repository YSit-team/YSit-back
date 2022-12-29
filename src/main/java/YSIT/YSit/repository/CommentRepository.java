package YSIT.YSit.repository;

import YSIT.YSit.domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepository {
    private final EntityManager em;
    public Long save(Comment com) {
        em.persist(com);
        return com.getId();
    }

    public Long maxRef() {
        int maxRef = em.createQuery("select max(c.ref) from Comment c")
                .getMaxResults();
        return (long) maxRef;
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }
    public List<Comment> findByArt(Long artId) {
        return em.createQuery("select c from Comment c where c.articleId = :articleId order by c.ref desc, refOrder asc", Comment.class)
                .setParameter("articleId", artId)
                .getResultList();
    }

}
