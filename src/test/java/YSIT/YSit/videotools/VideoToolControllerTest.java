//package YSIT.YSit.videotools;
//
//import YSIT.YSit.user.SchoolCategory;
//import YSIT.YSit.user.entity.User;
//import YSIT.YSit.user.service.UserService;
//import YSIT.YSit.videotools.entity.VideoTool;
//import YSIT.YSit.videotools.service.VideoToolService;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
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
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//@AutoConfigureMockMvc
//public class VideoToolControllerTest {
//    @Autowired
//    private VideoToolService videoToolService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private MockMvc mvc;
//
//    @BeforeEach
//    public void setting() throws Exception {
//        User writer = User.builder()
//                .loginId("tester")
//                .loginPw("pw")
//                .name("test")
//                .schoolCategory(SchoolCategory.STUDENT)
//                .build();
//
//        // 회원가입
//        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
//        query_param.add("loginId", writer.getLoginId());
//        query_param.add("loginPw", writer.getLoginPw());
//        query_param.add("name", writer.getName());
//        query_param.add("rank", String.valueOf(false));
//
//        mvc.perform(post("/api/user/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(query_param))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.loginId").value(writer.getLoginId()))
//                .andExpect(jsonPath("$.loginPw").value(userService.encryption(writer.getLoginPw())))
//                .andExpect(jsonPath("$.name").value(writer.getName()))
//                .andExpect(jsonPath("$.schoolCategory").value("STUDENT"));
//
//        // 로그인
//        mvc.perform(post("/api/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("loginId", writer.getLoginId())
//                        .param("loginPw", writer.getLoginPw()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.loginId").value(writer.getLoginId()))
//                .andExpect(jsonPath("$.loginPw").value(userService.encryption(writer.getLoginPw())));
//
//        List <User> temp_U = userService.findLoginId(writer.getLoginId());
//
//        User tester = null;
//        int cnt = 0;
//        for (User user : temp_U) {
//            tester = user;
//            cnt += 1;
//        }
//        if (cnt != 1) {
//            fail("유저 에러 발생");
//        }
//
//        String name = "VSLR";
//        int quantity = 10;
//
//        VideoTool videoTool = VideoTool.builder()
//                .name(name)
//                .quantity(quantity)
//                .maxQuantity(quantity)
//                .user(tester)
//                .build();
//
//        // 기자재 생성
//        MultiValueMap<String, String> query_param_VT = new LinkedMultiValueMap<>();
//        query_param_VT.add("name", videoTool.getName());
//        query_param_VT.add("quantity", String.valueOf(videoTool.getQuantity()));
//
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("Id", tester.getId());
//
//        mvc.perform(post("/api/videoTool/videoTools")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(query_param_VT)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(videoTool.getName()));
//    }
//
//    @Test
//    public void save() throws Exception {
//        User writer = User.builder()
//                .loginId("tester")
//                .loginPw("pw")
//                .name("test")
//                .schoolCategory(SchoolCategory.STUDENT)
//                .build();
//
//        // 회원가입
//        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
//        query_param.add("loginId", writer.getLoginId());
//        query_param.add("loginPw", writer.getLoginPw());
//        query_param.add("name", writer.getName());
//        query_param.add("rank", String.valueOf(false));
//
//        mvc.perform(post("/api/user/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(query_param))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.loginId").value(writer.getLoginId()))
//                .andExpect(jsonPath("$.loginPw").value(userService.encryption(writer.getLoginPw())))
//                .andExpect(jsonPath("$.name").value(writer.getName()))
//                .andExpect(jsonPath("$.schoolCategory").value("STUDENT"));
//
//        // 로그인
//        mvc.perform(post("/api/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("loginId", writer.getLoginId())
//                        .param("loginPw", writer.getLoginPw()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.loginId").value(writer.getLoginId()))
//                .andExpect(jsonPath("$.loginPw").value(userService.encryption(writer.getLoginPw())));
//
//        List <User> temp_U = userService.findLoginId(writer.getLoginId());
//
//        User tester = null;
//        int cnt = 0;
//        for (User user : temp_U) {
//            tester = user;
//            cnt += 1;
//        }
//        if (cnt != 1) {
//            fail("유저 에러 발생");
//        }
//
//        String name = "VSLR";
//        int quantity = 10;
//
//        VideoTool videoTool = VideoTool.builder()
//                .name(name)
//                .quantity(quantity)
//                .maxQuantity(quantity)
//                .user(tester)
//                .build();
//
//        // 기자재 생성
//        MultiValueMap<String, String> query_param_VT = new LinkedMultiValueMap<>();
//        query_param_VT.add("name", videoTool.getName());
//        query_param_VT.add("quantity", String.valueOf(videoTool.getQuantity()));
//
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("Id", tester.getId());
//
//        mvc.perform(post("/api/videoTool/videoTools")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(query_param_VT)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(videoTool.getName()));
//    }
//
//    @Test
//    public void list() throws Exception {
//        save();
//        mvc.perform(get("/api/videoTool/videoToolList")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}