package YSIT.YSit.videotools.controller;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import YSIT.YSit.videotools.dto.VideoToolForm;
import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.service.VideoToolService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videoTool")
@RequiredArgsConstructor
@CrossOrigin()
@Slf4j
public class VideoToolController {
    private final VideoToolService videoToolService;

    @GetMapping("/videoToolList") // 장비 목록
    public ResponseEntity list(HttpServletRequest request) {
        List<VideoTool> videoTools = videoToolService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(videoTools);
    }
}
