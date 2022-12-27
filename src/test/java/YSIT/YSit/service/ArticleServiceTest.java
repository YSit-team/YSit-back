package YSIT.YSit.service;

import YSIT.YSit.domain.Article;
import YSIT.YSit.domain.ArticleStatus;
import YSIT.YSit.domain.Board;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.ArticleRepository;
import YSIT.YSit.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class ArticleServiceTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager em;

    @Test
    public void saveTest() {
        String title = "게시물1";
        String body = "내용1";
        Board category = Board.자유;
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw("TEST")
                .build();
        userService.register(user);
        ArticleStatus status = ArticleStatus.PUBLIC;
        Article article = Article.builder()
                .title(title)
                .body(body)
                .category(category)
                .user(user)
                .status(status)
                .build();
        articleService.save(article);
        Article article1 = articleRepository.findOne(article.getId());
        if (Objects.isNull(article1)) {
            fail("실패");
        }
    }

    @Test
    public void findByTitle() {
        String title = "게시물1";
        String body = "내용1";
        Board category = Board.자유;
        ArticleStatus status = ArticleStatus.PUBLIC;
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw("TEST")
                .build();
        userService.register(user);
        Article article = Article.builder()
                .title(title)
                .body(body)
                .category(category)
                .user(user)
                .status(status)
                .build();
        articleService.save(article);

        List<Article> articleList = articleRepository.findByTitle("게시물");
        if (articleList.isEmpty()) {
            fail("실패");
        }
    }
}