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

import java.util.List;

@RestController
@RequestMapping("/api/rental")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/rentals")
    public ResponseEntity save(@ModelAttribute RentalForm rentalForm,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("Id");

        Rental rental = Rental.builder()
                .headCnt(rentalForm.getHeadCount())
                .rentalTools(rentalForm.getRentalTools())
                .uploader(id)
                .rentalDate(rentalForm.getRentalDate())
                .returnDate(rentalForm.getReturnDate())
                .reason(rentalForm.getReason())
                .phoneNum(rentalForm.getPhoneNum())
                .status(RentalStatus.wait)
                .build();

        log.info(String.valueOf(rental));

        rentalService.save(rental);
        return ResponseEntity.status(HttpStatus.OK).body(rental);
    }

    @PostMapping("/rentalHistory")
    public ResponseEntity history(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("Id");

        List<Rental> res = rentalService.findRequestById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
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
