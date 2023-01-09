package YSIT.YSit.controller;


import YSIT.YSit.controller.user.ArticleController;
import YSIT.YSit.domain.ArticleStatus;
import YSIT.YSit.domain.Board;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.ArticleService;
import YSIT.YSit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        User user = createUser();

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



    public User createUser() {
        User user1 = User.builder()
                .name("test")
                .loginId("test")
                .loginPw("test")
                .build();
        userService.register(user1);
        return user1;
    }
}