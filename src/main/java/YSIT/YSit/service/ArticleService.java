package YSIT.YSit.service;

import YSIT.YSit.domain.Article;
import YSIT.YSit.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Transactional
    public Long save(Article article) {
        articleRepository.save(article);
        return article.getId();
    }

    public List<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    public List<Article> findBybody(String body) {
        return articleRepository.findByBody(body);
    }
}
