package YSIT.YSit.board.comment.repository;

import YSIT.YSit.board.comment.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Ref;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepository {
    private final EntityManager em;
    public String save(Comment com) {
        em.persist(com);
        return com.getId();
    }

    public Long findMaxRef() {
        List<Comment> comments = em.createQuery("select c from Comment c", Comment.class)
                .getResultList();

        return listInFindMaxRef(comments);
    }

    public Long listInFindMaxRef(List<Comment> comments) {
        Long maxRef = 0L;
        for (Comment comment : comments) {
            if (maxRef < comment.getRef()) {
                maxRef = comment.getRef();
            }
        }
        return maxRef;
    }

    public List<Comment> findByRefAndOverRefOrder(Long ref, Long refOrder) {
        return em.createQuery("select c from Comment c where c.ref = :ref and c.refOrder >= :refOrder", Comment.class)
                .setParameter("ref", ref)
                .setParameter("refOrder", refOrder)
                .getResultList();
    }

    public Comment findOne(String id) {
        return em.find(Comment.class, id);
    }
    public List<Comment> findByArt(String artId) {
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
