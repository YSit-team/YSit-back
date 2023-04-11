package YSIT.YSit.videotools.controller;

import YSIT.YSit.user.service.UserService;
import YSIT.YSit.videotools.RentalStatus;
import YSIT.YSit.videotools.dto.RentalForm;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/rental")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/rentals")
    @ResponseBody
    public ResponseEntity save(@RequestBody HashMap<String, String> map) {
        Rental rental = Rental.builder()
                .headCnt(map.get("headCount"))
                .rentalTools(null)
                .uploader(map.get("uploader"))
                .rentalDate(map.get("rentalDate"))
                .returnDate(map.get("returnDate"))
                .reason(map.get("reason"))
                .status(RentalStatus.wait)
                .build();
        String rentalID = rentalService.save(rental);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/rentalHistory")
    public ResponseEntity history(HttpServletRequest request) {
        List<Rental> res = rentalService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/rentalDetail")
    public ResponseEntity detail(@RequestBody HashMap<String, String> map) {
        List<Rental> res = rentalService.findRequestById(map.get("rentalId"));
        Rental response = null;
        for (Rental resp : res) {
            response = resp;
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@ModelAttribute VideoToolForm videoToolForm,
                                 HttpServletRequest request) {
        String check = rentalService.deleteById(videoToolForm.getId());
        if (check != "Complete") {
            return ResponseEntity.status(HttpStatus.OK).body("fail");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }
    }
}
