package YSIT.YSit.comment;

import YSIT.YSit.board.article.Board;
import YSIT.YSit.board.article.entity.Article;
import YSIT.YSit.board.article.service.ArticleService;
import YSIT.YSit.board.article.ArticleStatus;
import YSIT.YSit.board.comment.entity.Comment;
import YSIT.YSit.board.comment.repository.CommentRepository;
import YSIT.YSit.board.comment.service.CommentService;
import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
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
    private EntityManager em;


    public Comment createCom(Long ref, Long step, Long refOrder, String parentId, String body, String articleId, String userId) {
        User writeUser = userService.findOne(userId);

        Comment comment = Comment.builder()
                .ref(ref)
                .step(step)
                .refOrder(refOrder)
                .parentId(parentId)
                .body(body)
                .articleId(articleId)
                .user(writeUser)
                .build();
        return comment;
    }
    @Test
    public void saveTest() {
        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw(userService.encryption("TEST"))
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
        Comment comment = createCom(0L,0L,0L,null,"Test",article.getId(), user.getId());
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
                .loginPw(userService.encryption("TEST"))
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
        Comment comment = createCom(0L,0L,0L,null,"Test",article.getId(), user.getId());
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
                .loginPw(userService.encryption("TEST"))
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
        Comment comment = createCom(0L,0L,0L,null,"Test",article.getId(), user.getId());
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
                .loginPw(userService.encryption("TEST"))
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
        Comment comment = createCom(0L,0L,0L,null,"Test",article.getId(), user.getId());
        commentService.save(comment);

        List<Comment> comments = commentService.findByArt(article.getId());
        if (comments.isEmpty()) {
            fail("실패");
        }
    }

    private Comment createCom_parentIsArt(String artId, String body, User user) {
        Long maxRef = commentService.getMaxRef();
        Comment comment = Comment.builder()
                .articleId(artId)
                .body(body)
                .user(user)
                .ref(++maxRef)
                .build();
        commentService.save(comment);
        return comment;
    }
    private Article createArts(String title, String body, Board category,
                               User user, ArticleStatus status) {
        Article art = Article.builder()
                .title(title)
                .body(body)
                .category(category)
                .user(user)
                .status(status)
                .build();
        articleService.save(art);
        return art;
    }

    private User createUser(String loginId, String loginPw, String name, SchoolCategory schoolCategory) {
        User user = User.builder()
                .loginId(loginId)
                .loginPw(loginPw)
                .name(name)
                .schoolCategory(schoolCategory)
                .build();
        userService.register(user);
        return user;
    }

    @Test
    public void addRefOrderTest() {
        String body = "test";
        User user = createUser("test", "test", "test", SchoolCategory.STUDENT);
        Article article = createArts("title", "body", Board.자유, user, ArticleStatus.PUBLIC);
        Comment parComment = createCom_parentIsArt(article.getId(), body, user);
        Comment childComment = Comment.builder()
                .articleId(article.getId())
                .user(user)
                .parentId(parComment.getId())
                .body(body)
                .ref(parComment.getRef())
                .step(parComment.getStep() + 1)
                .refOrder(parComment.getRefOrder() + 1)
                .build();
        commentService.save(childComment);

        commentService.addRefOrderForCommentRef(parComment.getRef(), parComment.getRefOrder() + 1);
        Comment addedChildComment = commentService.findOne(childComment.getId());
        if (addedChildComment.getRefOrder() != 2) {
            fail("실패");
        }
    }
}