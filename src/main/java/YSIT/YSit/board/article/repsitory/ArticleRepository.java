package YSIT.YSit.board.article.repsitory;

import YSIT.YSit.board.article.entity.Article;
import YSIT.YSit.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final EntityManager em;

    public void save(Article article) {
        em.persist(article);
    }

    public Article findOne(String id) {
        return em.find(Article.class, id);
    }

    public List<Article> findByUser(User user) {
        return em.createQuery("select a from Article a where a.user = :user order by a.id desc", Article.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Article> findAll() {
        return em.createQuery("select a from Article a order by a.id desc", Article.class)
                .getResultList();
    }

    public List<Article> findByTitle(String title) {
        String paramTitle = "%"+title+"%";
        return em.createQuery("select a from Article a where a.title like :title order by a.id desc", Article.class)
                .setParameter("title", paramTitle)
                .getResultList();
    }

    public List<Article> findByBody(String body) {
        String paramBody = "%"+body+"%";
        return em.createQuery("select a from Article a where a.body like :body order by a.id desc", Article.class)
                .setParameter("body", paramBody)
                .getResultList();
    }
    public void deleteById(String Id) {
        Article art = em.find(Article.class, Id);
        em.remove(art);
    }
}
