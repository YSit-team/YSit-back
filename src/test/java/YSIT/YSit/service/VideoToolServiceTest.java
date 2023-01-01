package YSIT.YSit.service;

import YSIT.YSit.domain.VideoTool;
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
    @Test
    public void saveTest() {
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .build();
        videoToolService.save(videoTool);
        VideoTool check = videoToolService.findOne(videoTool.getId());
        if (Objects.isNull(check)) {
            fail("실패");
        }
    }

    @Test
    public void findByNameTest() {
        VideoTool videoTool = VideoTool.builder()
                .name("카메라")
                .quantity(10)
                .build();
        videoToolService.save(videoTool);
        List<VideoTool> check = videoToolService.findByName("카메라");
        if (Objects.isNull(check)) {
            fail("실패");
        }
    }
}