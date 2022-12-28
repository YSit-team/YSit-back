package YSIT.YSit.service;

import YSIT.YSit.domain.Article;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Transactional(readOnly = false)
    public Long save(Article article) {
        List<Article> articles = articleRepository.findByTitle(article.getTitle());
        if (!articles.isEmpty()){
            throw new IllegalStateException("이미 있는 제목입니다");
        }
        articleRepository.save(article);
        return article.getId();
    }

    @Transactional(readOnly = false) // Dirty Check
    public void updateArticle(Article updateData) {
        Article article = articleRepository.findOne(updateData.getId());
        if (updateData.getTitle() != null) {
            article.changeTitle(updateData.getTitle());
        }
        if (updateData.getBody() != null) {
            article.changeBody(updateData.getBody());
        }
        if (updateData.getStatus() != null) {
            article.changeStatus(updateData.getStatus());
        }
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
    public List<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }
    public List<Article> findByLoginId(String loginId) {
        return articleRepository.findByLoginId(loginId);
    }
    public List<Article> findByBody(String body) {
        return articleRepository.findByBody(body);
    }
    public Article findOne(Long id) {
        return articleRepository.findOne(id);
    }
}