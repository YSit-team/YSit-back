package YSIT.YSit.service;

import YSIT.YSit.domain.*;
import YSIT.YSit.repository.CommentRepository;
import YSIT.YSit.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;


    public Comment createCom(Long ref, Long step, Long refOrder, Long parentId, String body, Long articleId, String writeUser) {
        Comment comment = Comment.builder()
                .ref(ref)
                .step(step)
                .refOrder(refOrder)
                .parentId(parentId)
                .body(body)
                .articleId(articleId)
                .writeUser(writeUser)
                .build();
        return comment;
    }
    @Test
    public void saveTest() {
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw(userRepository.encryption("TEST"))
                .build();
        userService.register(user);
        Article article = Article.builder()
                .title("TEST")
                .body("TEST")
                .category(Board.자유)
                .user(user)
                .status(ArticleStatus.PUBLIC)
                .build();
        articleService.save(article);
        Comment comment = createCom(0L,0L,0L,0L,"Test",article.getId(),user.getLoginId());
        commentService.save(comment);
        Comment findCom = commentService.findOne(comment.getId());
        if (Objects.isNull(findCom)){
            fail("실패");
        }
    }

    @Test
    public void getMaxRefTest() {
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw(userRepository.encryption("TEST"))
                .build();
        userService.register(user);
        Article article = Article.builder()
                .title("TEST")
                .body("TEST")
                .category(Board.자유)
                .user(user)
                .status(ArticleStatus.PUBLIC)
                .build();
        articleService.save(article);
        Comment comment = createCom(0L,0L,0L,0L,"Test",article.getId(),user.getLoginId());
        commentService.save(comment);

        Long maxRef = commentService.getMaxRef();
        if (maxRef != 0L) {
            fail("실패");
        }
    }

    @Test
    public void deleteComment() {
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw(userRepository.encryption("TEST"))
                .build();
        userService.register(user);
        Article article = Article.builder()
                .title("TEST")
                .body("TEST")
                .category(Board.자유)
                .user(user)
                .status(ArticleStatus.PUBLIC)
                .build();
        articleService.save(article);
        Comment comment = createCom(0L,0L,0L,0L,"Test",article.getId(),user.getLoginId());
        commentService.save(comment);
        commentService.delCommentByUser(comment);
        em.flush();

        Comment check = commentService.findOne(comment.getId());
        if (!Objects.equals(check.getBody(), "삭제되었습니다")) {
            fail("실패");
        }
    }

    @Test
    public void findByArtTest() {
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw(userRepository.encryption("TEST"))
                .build();
        userService.register(user);
        Article article = Article.builder()
                .title("TEST")
                .body("TEST")
                .category(Board.자유)
                .user(user)
                .status(ArticleStatus.PUBLIC)
                .build();
        articleService.save(article);
        Comment comment = createCom(0L,0L,0L,0L,"Test",article.getId(),user.getLoginId());
        commentService.save(comment);

        List<Comment> comments = commentService.findByArt(article.getId());
        if (comments.isEmpty()) {
            fail("실패");
        }
    }
}