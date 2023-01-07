package YSIT.YSit.controller.user;

import YSIT.YSit.controller.form.UserForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // MockMVC
public class UserControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    private static String baseUrl = "/api";

    @Test
    public void login() throws Exception {
        String loginId = "test";
        String loginPw = "test";
        String name = "test";

        // Object -> Json
        String body = mapper.writeValueAsString(
                UserForm.builder()
                        .loginId(loginId)
                        .loginPw(loginPw)
                        .name(name)
                        .build());

        mvc.perform(post(baseUrl + "/user/login")
                .content(body) // HTTP body
                .contentType(MediaType.APPLICATION_JSON) // 데이터타입
            )
            .andExpect(status().isOk())
            .andExpect(content().string("1"));
    }
}