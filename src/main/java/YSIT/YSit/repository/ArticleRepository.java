package YSIT.YSit.repository;

import YSIT.YSit.domain.Article;
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

    public Article findOne(Long id) {
        return em.find(Article.class, id);
    }

    public List<Article> findByLoginId(String loginId) {
        return em.createQuery("select a from Article a where a.writeUser = :writeUser", Article.class)
                .setParameter("writeUser", loginId)
                .getResultList();
    }

    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }

    public List<Article> findByTitle(String title) {
        String paramTitle = "%"+title+"%";
        return em.createQuery("select a from Article a where a.title like :title", Article.class)
                .setParameter("title", paramTitle)
                .getResultList();
    }

    public List<Article> findByBody(String body) {
        String paramBody = "%"+body+"%";
        return em.createQuery("select a from Article a where a.body like :body", Article.class)
                .setParameter("body", paramBody)
                .getResultList();
    }
}
