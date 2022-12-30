package YSIT.YSit.repository;

import YSIT.YSit.domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        List<Comment> comments = em.createQuery("select c from Comment c", Comment.class)
                .getResultList();

        Long maxRef = 0L;
        for (Comment comment : comments) {
            if (maxRef < comment.getRef()) {
                maxRef = comment.getRef();
            }
        }
        return maxRef;
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }
    public List<Comment> findByArt(Long artId) {
        return em.createQuery("select c from Comment c where c.articleId = :articleId order by c.ref desc, refOrder asc", Comment.class)
                .setParameter("articleId", artId)
                .getResultList();
    }

    public Comment findByRefOrder(Long refOrder) {
        List<Comment> finds = em.createQuery("select c from Comment c where c.refOrder = :refOrder", Comment.class)
                .setParameter("refOrder", refOrder)
                .getResultList();
        Comment findRefOrder = null;
        if (finds.size() != 0) {
            findRefOrder = finds.get(0);
        }
        return findRefOrder;
    }
}
