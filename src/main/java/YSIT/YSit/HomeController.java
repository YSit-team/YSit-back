package YSIT.YSit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
    @GetMapping("")
    public ResponseEntity home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        if (userId == null || userId == 0){
            return ResponseEntity.status(HttpStatus.OK).body("로그인 상태가 아닙니다");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("로그인 상태입니다");
        }
    }
}
