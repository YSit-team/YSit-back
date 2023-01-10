package YSIT.YSit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)	// (1)
@WebMvcTest(controllers = HomeController.class)	// (2)
public class HomeControllerTest {

    @Autowired	// (3)
    private MockMvc mvc;	// (4)

    @Test
    public void home() throws Exception {
        mvc.perform(get("/api"))	// (5)
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 상태가 아닙니다")); // (6)(7)
    }

    @Test
    public void homeLogin() throws Exception {
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

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", 1L);

        mvc.perform(get("/api")
                .session(session))
                .andExpect(content().string("로그인 상태입니다"));
    }
}