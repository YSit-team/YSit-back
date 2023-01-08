package YSIT.YSit.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)	// (1)
@WebMvcTest(controllers = HomeController.class)	// (2)
public class HomeControllerTest {

    @Autowired	// (3)
    private MockMvc mvc;	// (4)

    @Test
    public void hello() throws Exception{
        String hello = "hello";
        mvc.perform(get("/api/hello"))	// (5)
                .andExpect(status().isOk())	// (6)(7)
                .andExpect(content().string(hello));	// (8)
    }
}