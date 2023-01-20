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

    @Test
    public void addQuantity() {
        User user = createUser("test","test","test",SchoolCategory.STUDENT);
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        videoToolService.addQuantity(videoTool.getId(), 2);
        if (videoTool.getQuantity() != 12) {
            fail("수량이 추가되지 않았습니다.");
        }
    }

    @Test
    public void addMaxQuantity() {
        User user = createUser("test","test","test",SchoolCategory.STUDENT);
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        videoToolService.addMaxQuantity(videoTool.getId(), 2);
        if (videoTool.getMaxQuantity() != 12) {
            System.out.printf("%d", videoTool.getMaxQuantity());
            fail("수량이 추가되지 않았습니다.");
        }
    }

    @Test
    public void removeQuantity() {
        User user = createUser("test","test","test",SchoolCategory.STUDENT);
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        videoToolService.removeQuantity(videoTool.getId(), 2);
        if (videoTool.getQuantity() != 8) {
            fail("수량이 적어지지 않았습니다");
        }
    }

    @Test
    public void removeQuantityException() {
        User user = createUser("test","test","test",SchoolCategory.STUDENT);
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);
        try {
            videoToolService.removeQuantity(videoTool.getId(), 11);
        } catch (IllegalStateException e) {
            return;
        }
        fail("예외가 발생하지 않았습니다");
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