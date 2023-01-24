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
    private final UserService userService;
    @PostMapping("/videoTools")
    public ResponseEntity save(@ModelAttribute VideoToolForm form, HttpServletRequest request) {
        List<VideoTool> duplicateValidation = videoToolService.findByName(form.getName());
        if (duplicateValidation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("이미 있는 기자재명입니다");
        }
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("Id");
        User writer = userService.findOne(id);

        VideoTool videoTool = VideoTool.builder()
                .name(form.getName())
                .quantity(form.getMaxQuantity())
                .maxQuantity(form.getMaxQuantity())
                .user(writer)
                .build();
        videoToolService.save(videoTool);
        return ResponseEntity.status(HttpStatus.OK).body(videoTool);
    }

    @GetMapping("/videoToolList")
    public ResponseEntity list() {
        List<VideoTool> videoTools = videoToolService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(videoTools);
    }

    @GetMapping("/videoToolList/{searchName}")
    public ResponseEntity searchList(@PathVariable("searchName") String searchName) {
        List<VideoTool> videoTools = videoToolService.findByName(searchName);
        return ResponseEntity.status(HttpStatus.OK).body(videoTools);
    }
}
