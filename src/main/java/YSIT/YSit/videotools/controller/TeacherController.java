package YSIT.YSit.videotools.controller;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import YSIT.YSit.videotools.RentalStatus;
import YSIT.YSit.videotools.dto.ManageForm;
import YSIT.YSit.videotools.dto.VideoToolForm;
import YSIT.YSit.videotools.entity.Rental;
import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.service.RentalService;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class TeacherController {
    private final RentalService rentalService;
    private final VideoToolService videoToolService;
    private final UserService userService;
    @GetMapping("/rentals")
    public ResponseEntity list() {
        List<Rental> rentals = rentalService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }

    @GetMapping("/rental/{rentalId}")
    public ResponseEntity viewRent(@PathVariable("rentalId") String rentId) {
        Rental rentArt = rentalService.findOne(rentId);
        return ResponseEntity.status(HttpStatus.OK).body(rentArt);
    }

    @PostMapping("/rentals/manage")
    public ResponseEntity rentManage(@ModelAttribute ManageForm manageForm) {
        Rental rentArt = rentalService.findOne(manageForm.getRentId());
        if (manageForm.getManage() == true) {
            rentArt.changeStatus(RentalStatus.accept);
            // 카카오톡 알림
        }
        else {
            rentArt.changeStatus(RentalStatus.reject);
            // 카카오톡 알림
        }
        return ResponseEntity.status(HttpStatus.OK).body(rentArt);
    }
    @PostMapping("/videoTools") // 장비 생성
    public ResponseEntity save(@ModelAttribute VideoToolForm form, HttpServletRequest request) {
        List<VideoTool> duplicateValidation = videoToolService.findByName(form.getName());
        if (!duplicateValidation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("이미 있는 기자재명입니다");
        }

        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("Id");
        User writer = userService.findOne(id);

        VideoTool videoTool = VideoTool.builder()
                .name(form.getName())
                .quantity(form.getQuantity())
                .maxQuantity(form.getQuantity())
                .user(writer)
                .build();
        videoToolService.save(videoTool);
        return ResponseEntity.status(HttpStatus.OK).body(videoTool);
    }
}
