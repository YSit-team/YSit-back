package YSIT.YSit.videotools;

import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.service.VideoToolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class VideoToolServiceTest {
    @Autowired
    private VideoToolService videoToolService;
    @Autowired
    private UserService userService;
    @Test
    public void saveTest() {
        User user = createUser("test","test","test",SchoolCategory.TEACHER);

        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        VideoTool check = videoToolService.findOne(videoTool.getId());
        if (Objects.isNull(check)) {
            fail("실패");
        }
    }

    @Test
    public void findAll() {
        User user = createUser("test","test","test",SchoolCategory.TEACHER);
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        List<VideoTool> videoTools = videoToolService.findAll();
        if (Objects.isNull(videoTools) || videoTools.isEmpty()) {
            fail("검색 실패");
        }
    }

    @Test
    public void findByNameTest() {
        User user = createUser("test","test","test",SchoolCategory.TEACHER);
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        List<VideoTool> check = videoToolService.findByName("카메라");
        if (Objects.isNull(check)) {
            fail("실패");
        }
    }

    private User createUser(String loginId, String loginPw, String name, SchoolCategory schoolCategory) {
        User user = User.builder()
                .loginId(loginId)
                .loginPw(loginPw)
                .name(name)
                .schoolCategory(schoolCategory)
                .build();
        userService.register(user);
        return user;
    }
}