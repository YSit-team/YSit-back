package YSIT.YSit.videotools;

import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import YSIT.YSit.videotools.entity.Rental;
import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.service.RentalService;
import YSIT.YSit.videotools.service.VideoToolService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
public class RentalControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private VideoToolService videoToolService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private MockMvc mvc;
//    @Test
//    public void save() throws Exception {
//        User user = null;
//        VideoTool videoTool = null;
//        Rental rent = null;
//
//        user = User.builder()
//                .loginId("tester")
//                .loginPw("pw")
//                .name("test")
//                .schoolCategory(SchoolCategory.STUDENT)
//                .build();
//        userService.register(user);
//
//        videoTool = VideoTool.builder()
//                .name("VSLR")
//                .quantity(10)
//                .maxQuantity(10)
//                .user(user)
//                .build();
//        videoToolService.save(videoTool);
//
//
//        List<String> rentalTools = new ArrayList<String>();
//        rentalTools.add(videoTool.getName());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//        LocalDate rentalDate = LocalDate.parse("2023-03-13 11:11:00", formatter);
//        LocalDate returnDate = LocalDate.parse("2023-03-14 12:12:00", formatter);
//
//        rent = Rental.builder()
//                .rentalTools(rentalTools)
//                .uploader(user.getId())
//                .headCnt(3)
//                .rentalDate(rentalDate.atStartOfDay())
//                .returnDate(returnDate.atStartOfDay())
//                .reason(null)
//                .status(RentalStatus.wait)
//                .build();
//
//        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
//        query_param.add("headCount" , String.valueOf(rent.getHeadCnt()));
//        query_param.add("rentalTools", rent.getRentalTools().toString());
//        query_param.add("rentalDate", String.valueOf(rent.getRentalDate()));
//        query_param.add("returnDate", String.valueOf(rent.getReturnDate()));
//        query_param.add("phoneNum", String.valueOf(12345));
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("Id", user.getId());
//
//        mvc.perform(post("/api/rental/rentals")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .params(query_param)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.uploader").value(user.getId()));
//    }

    @Test
    public void history() {
    }

    @Test
    public void delete() {
    }
}