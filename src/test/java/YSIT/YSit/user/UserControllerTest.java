package YSIT.YSit.user;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService userService;

    @Test
    public void register() throws Exception {
        // given
        String loginId = "test";
        String loginPw = "test";
        String name = "test";
        Boolean rank = false;

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("loginId",loginId);
        query_param.add("loginPw",loginPw);
        query_param.add("name", name);
        query_param.add("rank", String.valueOf(rank));

        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .params(query_param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value(loginId))
                .andExpect(jsonPath("$.loginPw").value(userService.encryption(loginPw)))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.schoolCategory").value("STUDENT"));
        }


    @Test
    public void registerException_sameID() throws Exception{
        // given
        String loginId = "test";
        String loginPw = "test";
        String name = "test";
        Boolean rank = false;

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("loginId",loginId);
        query_param.add("loginPw",loginPw);
        query_param.add("name", name);
        query_param.add("rank", String.valueOf(rank));

        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .params(query_param));

        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .params(query_param))
                .andExpect(status().isConflict());
    }

    @Test
    public void registerException_RankIsNull() throws Exception{
        // given
        String loginId = "test";
        String loginPw = "test";
        String name = "test";

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("loginId",loginId);
        query_param.add("loginPw",loginPw);
        query_param.add("name", name);

        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .params(query_param));

        mvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(query_param))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void login() throws Exception{

        String loginId = "test";
        String loginPw = "test";
        String name = "test";

        register();

        mvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("loginId", loginId)
                        .param("loginPw", loginPw))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value(loginId))
                .andExpect(jsonPath("$.loginPw").value(userService.encryption(loginPw)))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.schoolCategory").value("STUDENT"));
    }
//
//    @Test
//    public void loginException() throws Exception {
//        String loginId = "test";
//        String loginPw = "test";
//
//        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
//        query_param.add("loginId",loginId);
//        query_param.add("loginPw",loginPw);
//
//        mvc.perform(post("/api/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("loginId", loginId)
//                        .param("loginPw", loginPw))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void userUpdate() throws Exception {
        // given
        String updateLoginId = "Complete";
        String updateLoginPw = "Complete";
        String updateName = "Complete";

        User user = User.builder()
                .loginId("test")
                .loginPw("test")
                .name("test")
                .build();
        userService.register(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        mvc.perform(post("/api/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("loginId", updateLoginId)
                        .param("loginPw", updateLoginPw)
                        .param("name", updateName)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value(updateLoginId))
                .andExpect(jsonPath("$.loginPw").value(userService.encryption(updateLoginPw)));
    }

    @Test
    public void userUpdateException() throws Exception {
        // given
        String updateLoginId = "Complete";
        String updateLoginPw = "Complete";
        String updateName = "Complete";
        Boolean rank = true;
        register(); // 업데이트할 유저

        // when
        User user = User.builder()
                .loginPw(updateLoginPw)
                .loginId(updateLoginId)
                .name(updateName)
                .build();
        userService.register(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", user.getId());

        mvc.perform(post("/api/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("loginId", updateLoginId)
                        .param("loginPw", updateLoginPw)
                        .param("name", updateName)
                        .session(session))
                .andExpect(status().isConflict());
    }
}