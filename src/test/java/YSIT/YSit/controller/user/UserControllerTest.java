package YSIT.YSit.controller.user;

import YSIT.YSit.controller.form.UserForm;
import YSIT.YSit.domain.SchoolCategory;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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
    @Transactional
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

    @Test
    public void loginException() throws Exception {
        String loginId = "test";
        String loginPw = "test";

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("loginId",loginId);
        query_param.add("loginPw",loginPw);
        query_param.add("name", null);
        query_param.add("rank", null);

        mvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("loginId", loginId)
                        .param("loginPw", loginPw))
                .andExpect(status().isBadRequest());
    }
}