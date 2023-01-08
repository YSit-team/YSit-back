package YSIT.YSit.controller.user;

import YSIT.YSit.controller.form.UserForm;
import YSIT.YSit.domain.SchoolCategory;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/user/register") // 회원가입 기능
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
                .loginPw(userService.encryption(form.getLoginPw()))
                .schoolCategory(schoolCategory)
                .regDate(LocalDateTime.now())
                .build();
        userService.register(user);
        User responseUser = userService.findOne(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PostMapping("/user/login") // 로그인 기능
    public ResponseEntity login(
                                HttpServletRequest request,
            @RequestParam("loginId") String loginId, @RequestParam("loginPw") String loginPw) {
        List<User> matchLogins = userService.matchLogins(loginId, loginPw);
        if (matchLogins.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("암호가 일치하지 않습니다");
        } else {
            User responseUser = matchLogins.get(0);

            HttpSession session = request.getSession();
            session.setAttribute("Id", responseUser.getId());

            return ResponseEntity.status(HttpStatus.OK).body(responseUser);
        }
    }

//    @GetMapping("/user/userList") // 회원 목록
//    public String userListForm(Model model) {
//        List<User> users = userService.findUserAll();
//        model.addAttribute("users", users);
//        model.addAttribute("userList", new UserListForm());
//        return "users/user/UserList";
//    }
//
//    @PostMapping("/user/userList")
//    public String userList(@ModelAttribute UserListForm form, Model model) {
//        int check_bool = 0;
//        List<User> findList = null;
//
//        if (form.getStudent()) {
//            findList = userService.findStudentAll();
//            check_bool += 1;
//        }
//        if (form.getTeacher()) {
//            findList = userService.findTeacherAll();
//            check_bool += 1;
//        }
//        if (check_bool >= 2 || check_bool <= 0) {
//            findList = userService.findUserAll();
//        }
//
//        if (findList != null) {
//            model.addAttribute("users", findList);
//        } else {
//            User user = User.builder()
//                    .name(null)
//                    .loginId(null)
//                    .loginPw(null)
//                    .schoolCategory(null)
//                    .regDate(null)
//                    .build();
//            model.addAttribute("users", user);
//        }
//        model.addAttribute("userList", new UserListForm());
//        return "users/user/UserList";
//    }
//
//    @GetMapping("/user/userUpdate")
//    public String updatePageForm(Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Long id = (Long) session.getAttribute("Id");
//        User user = userService.findOne(id);
//
//        model.addAttribute("user", user);
//        model.addAttribute("userForm", new UserForm());
//        return "users/user/UserUpdate";
//    }
//    @PostMapping("/user/userUpdate")
//    public String userUpdate(@ModelAttribute UserForm form, BindingResult result,
//                             Model model, HttpServletRequest request) {
//        List<User> valid = userService.findLoginId(form.getLoginId());
//        HttpSession session = request.getSession();
//        Long id = (Long) session.getAttribute("Id");
//        User user = userService.findOne(id);
//
//        if (!valid.isEmpty()) {
//            result.rejectValue("loginId", "sameId");
//            model.addAttribute("user", user);
//            return "users/user/UserUpdate";
//        }
//
//        User user2 = User.builder()
//                .id(id)
//                .name(form.getName())
//                .loginId(form.getLoginId())
//                .loginPw(form.getLoginPw())
//                .build();
//        userService.updateUser(user2);
//
//        return "redirect:/";
//    }

    @GetMapping("/user/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }
}
