package YSIT.YSit.article;


import YSIT.YSit.board.article.entity.Article;
import YSIT.YSit.board.article.ArticleStatus;
import YSIT.YSit.board.Board;
import YSIT.YSit.board.article.service.ArticleService;
import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @Test
    public void write() throws Exception {
        String title = "test";
        String body = "test";
        Board category = Board.자유;
        ArticleStatus status = ArticleStatus.PUBLIC;

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("title", title);
        query_param.add("body", body);
        query_param.add("category", String.valueOf(category));
        query_param.add("status", String.valueOf(status));

        User user = User.builder()
                .name("test")
                .loginId("test")
                .loginPw("test")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        mvc.perform(post("/api/article/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(query_param)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.body").value(body))
                .andExpect(jsonPath("$.category").value("자유"))
                .andExpect(jsonPath("$.status").value("PUBLIC"));
    }

    @Test
    public void write_sameTitle() throws Exception {
        User writeUser = User.builder()
                .name("writeUser")
                .loginId("writeUser")
                .loginPw("writeUser")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(writeUser);

        Article article = Article.builder()
                .title("test")
                .body("test")
                .category(Board.자유)
                .status(ArticleStatus.PUBLIC)
                .user(writeUser)
                .build();
        articleService.save(article);

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("title", "test");
        query_param.add("body", "test");
        query_param.add("category", String.valueOf(Board.자유));
        query_param.add("status", String.valueOf(ArticleStatus.PUBLIC));

        User user = User.builder()
                .name("test")
                .loginId("test")
                .loginPw("test")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        mvc.perform(post("/api/article/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(query_param)
                        .session(session))
                .andExpect(status().isConflict())
                .andExpect(content().string("이미 같은 제목의 게시물이 있습니다"));
    }

    @Test
    public void artList() throws Exception {
        write();
        User user = User.builder()
                .name("TEST2")
                .loginId("TEST2")
                .loginPw("TEST2")
                .build();
        userService.register(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());


        mvc.perform(get("/api/article/articleList/{bool_title}/{bool_body}/{searchBody}",
                        true, true, "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    public void view() throws Exception{

        User user = User.builder()
                .name("TEST")
                .loginId("TEST")
                .loginPw("TEST")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        Article article = Article.builder()
                .title("test")
                .body("test")
                .category(Board.자유)
                .status(ArticleStatus.PUBLIC)
                .user(user)
                .build();
        articleService.save(article);
        Article viewTarget = articleService.findOne(article.getId());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        mvc.perform(get("/api/article/articlePage/{articleId}/view", viewTarget.getId())
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    public void view_privateStudentException() throws Exception{
        User user = User.builder()
                .name("writeUser")
                .loginId("writeUser")
                .loginPw("writeUser")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        Article article = Article.builder()
                .title("test")
                .body("test")
                .category(Board.자유)
                .status(ArticleStatus.PRIVATE)
                .user(user)
                .build();
        articleService.save(article);

        Article viewTarget = articleService.findOne(article.getId());
        User viewUser = User.builder()
                .schoolCategory(SchoolCategory.STUDENT)
                .name("viewUser")
                .loginPw("viewUser")
                .loginId("viewUser")
                .build();
        userService.register(viewUser);

        MockHttpSession session2 = new MockHttpSession();
        session2.setAttribute("Id", viewUser.getId());


        mvc.perform(get("/api/article/articlePage/{articleId}/view", viewTarget.getId())
                        .session(session2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("학생은 비공개 게시물을 볼 수 없습니다"));
    }

    @Test
    public void view_privateTeacher() throws Exception{
        User user = User.builder()
                .name("writeUser")
                .loginId("writeUser")
                .loginPw("writeUser")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        Article article = Article.builder()
                .title("test")
                .body("test")
                .category(Board.자유)
                .status(ArticleStatus.PRIVATE)
                .user(user)
                .build();
        articleService.save(article);

        Article viewTarget = articleService.findOne(article.getId());
        User viewUser = User.builder()
                .schoolCategory(SchoolCategory.TEACHER)
                .name("viewUser")
                .loginPw("viewUser")
                .loginId("viewUser")
                .build();
        userService.register(viewUser);

        MockHttpSession session2 = new MockHttpSession();
        session2.setAttribute("Id", viewUser.getId());

        mvc.perform(get("/api/article/articlePage/{articleId}/view", viewTarget.getId())
                        .session(session2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        String updateTitle = "Complete";
        String updateBody = "Complete";

        User user = User.builder()
                .name("writeUser")
                .loginId("writeUser")
                .loginPw("writeUser")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        Article article = Article.builder()
                .title("test")
                .body("test")
                .category(Board.자유)
                .status(ArticleStatus.PRIVATE)
                .user(user)
                .build();
        articleService.save(article);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("title", updateTitle);
        query_param.add("body", updateBody);
        query_param.add("category", String.valueOf(Board.자유));
        query_param.add("status", String.valueOf(ArticleStatus.PUBLIC));

        mvc.perform(post("/api/article/articlePage/{articleId}/update",
                article.getId())
                .session(session)
                .params(query_param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updateTitle))
                .andExpect(jsonPath("$.body").value(updateBody));
    }

    @Test
    public void update_sameId() throws Exception {
        String updateTitle = "Complete";
        String updateBody = "Complete";

        User user = User.builder()
                .name("writeUser")
                .loginId("writeUser")
                .loginPw("writeUser")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        Article article = Article.builder()
                .title("test")
                .body("test")
                .category(Board.자유)
                .status(ArticleStatus.PRIVATE)
                .user(user)
                .build();
        articleService.save(article);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("title", updateTitle);
        query_param.add("body", updateBody);
        query_param.add("category", String.valueOf(Board.자유));
        query_param.add("status", String.valueOf(ArticleStatus.PUBLIC));

        Article origin = Article.builder()
                .title(updateTitle)
                .body(updateBody)
                .category(Board.자유)
                .status(ArticleStatus.PRIVATE)
                .user(user)
                .build();
        articleService.save(origin);

        mvc.perform(post("/api/article/articlePage/{articleId}/update",
                        article.getId())
                        .session(session)
                        .params(query_param))
                .andExpect(status().isConflict())
                .andExpect(content().string("이미 같은 제목의 게시물이 있습니다"));
    }
}