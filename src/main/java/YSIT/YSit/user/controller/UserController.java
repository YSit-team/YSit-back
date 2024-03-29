package YSIT.YSit.user.controller;

import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.dto.UserForm;
import YSIT.YSit.user.service.UserService;
import YSIT.YSit.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/register") // 회원가입 기능
    public ResponseEntity register(
                @ModelAttribute UserForm form) {
        if (form.getLoginId().isEmpty() || form.getLoginPw().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("값이 없습니다");
        } else if (form.getRank() == null || form.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("값이 없습니다");
        }

        List<User> tempUser = userService.doubleCheckLoginId(form.getLoginId());
        if(!tempUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 있는 아이디입니다");
        }

        SchoolCategory schoolCategory;

        if (form.getRank() == false){
            schoolCategory = SchoolCategory.STUDENT;
        } else {
            schoolCategory = SchoolCategory.TEACHER;
        }

        User user = User.builder()
                .name(form.getName())
                .loginId(form.getLoginId())
                .loginPw(form.getLoginPw())
                .schoolCategory(schoolCategory)
                .regDate(LocalDateTime.now())
                .build();
        userService.register(user);
        User responseUser = userService.findOne(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PostMapping("/login") // 로그인 기능
    public ResponseEntity login(
                                HttpServletRequest request,
                                @ModelAttribute UserForm form) {
        log.info("\nLoginID = {}\nLoginPW = {}", form.getLoginId(), form.getLoginPw());

        List<User> matchId = userService.findLoginId(form.getLoginId());
        if (Objects.isNull(matchId)) {
            return ResponseEntity.status(HttpStatus.OK).body("아이디가 일치하지 않습니다");
        }
        List<User> matchPw = userService.findLoginPw(form.getLoginPw());
        if (Objects.isNull(matchPw)) {
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 일치하지 않습니다");
        }
        Boolean matchLogin = userService.matchLogins(form.getLoginId(), form.getLoginPw());
        if (!matchLogin) {
            return ResponseEntity.status(HttpStatus.OK).body("암호가 일치하지 않습니다");
        }

        User resUser = null;
        for (User user : matchId) {
            resUser = user;
        }

        HttpSession session = request.getSession();
        session.setAttribute("Id", resUser.getId());

        System.out.printf("user = %s", resUser.getLoginId());
        return ResponseEntity.status(HttpStatus.OK).body(resUser);

    }

    @PostMapping("/update")
    public ResponseEntity userUpdate(
            @RequestParam("loginId") String loginId, @RequestParam("loginPw") String loginPw,
                             @RequestParam("name") String name, HttpServletRequest request) {
        List<User> valid = userService.findLoginId(loginId);
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("Id");
        User targetUser = userService.findOne(id);

        if (!valid.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 있는 아이디입니다");
        }

        User updateUser = User.builder()
                .id(targetUser.getId())
                .name(name)
                .loginId(loginId)
                .loginPw(userService.encryption(loginPw))
                .build();
        userService.updateUser(updateUser);

        User responseUser = userService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }
}
