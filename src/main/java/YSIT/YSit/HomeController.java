package YSIT.YSit;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="*", allowedHeaders = "*")
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    @GetMapping("")
    public String home(
//            HttpServletRequest request
    ) {
//        HttpSession session = request.getSession();
//        Long userId = (Long) session.getAttribute("Id");
//        if (userId == null || userId == 0){
//            return ResponseEntity.status(HttpStatus.OK).body("로그인 상태가 아닙니다");
//        } else {
//            return ResponseEntity.status(HttpStatus.OK).body("로그인 상태입니다");
//        }
        User user = User.builder()
                .loginId("H1")
                .loginPw("h1").build();

        return "forward:/index.html";
    }
}
