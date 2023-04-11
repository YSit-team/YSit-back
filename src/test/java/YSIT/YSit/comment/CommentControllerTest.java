//package YSIT.YSit.comment;
//
//import YSIT.YSit.board.article.ArticleStatus;
//import YSIT.YSit.board.article.Board;
//import YSIT.YSit.board.article.entity.Article;
//import YSIT.YSit.board.article.service.ArticleService;
//import YSIT.YSit.board.comment.entity.Comment;
//import YSIT.YSit.board.comment.service.CommentService;
//import YSIT.YSit.user.SchoolCategory;
//import YSIT.YSit.user.entity.User;
//import YSIT.YSit.user.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.util.Objects;
//
//import static org.junit.Assert.fail;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//public class CommentControllerTest {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ArticleService articleService;
//
//    private User createUser(String loginId, String loginPw, String name, SchoolCategory schoolCategory) {
//        User user = User.builder()
//                .loginId(loginId)
//                .loginPw(loginPw)
//                .name(name)
//                .schoolCategory(schoolCategory)
//                .build();
//        userService.register(user);
//        return user;
//    }
//    private Article createArts(String title, String body, Board category,
//                               User user, ArticleStatus status) {
//        Article art = Article.builder()
//                .title(title)
//                .body(body)
//                .category(category)
//                .user(user)
//                .status(status)
//                .build();
//        articleService.save(art);
//        return art;
//    }
//    private Comment createCom_parentIsArt(String artId, String body, User user) {
//        Long maxRef = commentService.getMaxRef();
//        Comment comment = Comment.builder()
//                .articleId(artId)
//                .body(body)
//                .user(user)
//                .ref(++maxRef)
//                .build();
//        commentService.save(comment);
//        return comment;
//    }
//
//    @Test
//    public void parentIsArt_write() throws Exception {
//        User user = createUser("test", "test", "test", SchoolCategory.STUDENT);
//        Article art = createArts("title","body",Board.자유, user, ArticleStatus.PUBLIC);
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("Id", user.getId());
//        String body = "TEST";
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("body", body);
//        params.add("articleId", art.getId());
//
//        mvc.perform(post("/api/comment/coms")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(params)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.ref").value(1))
//                .andExpect(jsonPath("$.articleId").value(art.getId()))
//                .andExpect(jsonPath("$.body").value(body));
//    }
//
//    @Test
//    public void parentIsCom_write() throws Exception {
//        User user = createUser("test", "test", "test", SchoolCategory.STUDENT);
//        Article art = createArts("title","body",Board.자유, user, ArticleStatus.PUBLIC);
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("Id", user.getId());
//        Comment parentCom = createCom_parentIsArt(art.getId(), "testComBody", user); // 1
//        String body = "TEST";
//
//        // 대댓글 작성 테스트
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 2
//        params.add("body", body);
//        params.add("articleId", art.getId());
//        params.add("parentId", parentCom.getId());
//
//        mvc.perform(post("/api/comment/coms")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(params)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.parentId").value(parentCom.getId()))
//                .andExpect(jsonPath("$.articleId").value(art.getId()))
//                .andExpect(jsonPath("$.body").value(body));
//
//        // 대댓글 RefOrder 증가 테스트
//        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>(); // 3
//        params2.add("body", body);
//        params2.add("articleId", art.getId());
//        params2.add("parentId", parentCom.getId());
//
//        mvc.perform(post("/api/comment/coms")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(params2)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.parentId").value(parentCom.getId()))
//                .andExpect(jsonPath("$.articleId").value(art.getId()))
//                .andExpect(jsonPath("$.body").value(body))
//                .andExpect(jsonPath("$.refOrder").value(parentCom.getRefOrder() + 1));
//
//        // 대대댓글 테스트
//        mvc.perform(post("/api/comment/coms") // 대댓글 두개 만들기
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(params2)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.parentId").value(parentCom.getId()))
//                .andExpect(jsonPath("$.articleId").value(art.getId()))
//                .andExpect(jsonPath("$.body").value(body));
//
//        // 1번 대댓글의 댓글 달기
//        Comment parentComment = commentService.findByRefOrder(1L);
//        MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>(); // 4
//        params3.add("body", "BODY");
//        params3.add("articleId", art.getId());
//        params3.add("parentId", parentComment.getId());
//
//        // 대대댓글 테스트
//        mvc.perform(post("/api/comment/coms")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(params3)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.parentId").value(parentComment.getId()))
//                .andExpect(jsonPath("$.articleId").value(art.getId()))
//                .andExpect(jsonPath("$.body").value("BODY"))
//                .andExpect(jsonPath("$.refOrder").value(parentComment.getRefOrder()+1));
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        String body = "test";
//        User user = createUser("test","test", "test", SchoolCategory.STUDENT);
//        Article article = createArts("title", "body", Board.자유, user, ArticleStatus.PUBLIC);
//        Comment comment = createCom_parentIsArt(article.getId(), body, user);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 4
//        params.add("id", comment.getId());
//
//        mvc.perform(post("/api/comment/delete")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(params))
//                .andExpect(status().isOk())
//                .andExpect(content().string("삭제되었습니다"));
//    }
//}