package YSIT.YSit.repository;

import YSIT.YSit.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }
    public Comment findOne(Long Comment_id) {
        return em.find(Comment.class, Comment_id);
    }
}
