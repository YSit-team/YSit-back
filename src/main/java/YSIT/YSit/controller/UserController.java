package YSIT.YSit.controller;

import YSIT.YSit.domain.SchoolCategory;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.UserRepository;
import YSIT.YSit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    @GetMapping("/user/register") // 회원가입 진입
    public String registerForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/Register";
    }
    @PostMapping("/user/register") // 회원가입 기능
    public String register(@Valid @ModelAttribute UserForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "/user/Register";
        }
        SchoolCategory schoolCategory;
//        log.info("\nrank = {}", form.getRank());
        if (form.getRank()){
            schoolCategory = SchoolCategory.STUDENT;
        } else {
            schoolCategory = SchoolCategory.TEACHER;
        }

        User user = User.builder()
                .name(form.getName())
                .loginId(form.getLoginId())
                .loginPw(userRepository.encryption(form.getLoginPw()))
                .schoolCategory(schoolCategory)
                .regDate(LocalDateTime.now())
                .build();
        userService.register(user);

        return "redirect:/";
    }

    @GetMapping("/user/login") // 로그인 진입
    public String loginForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/Login";
    }
    @PostMapping("/user/login") // 로그인 기능
    public String login(UserForm form) {
        try {
            userService.matchLogins(form.getLoginId(), form.getLoginPw());
        } catch (IllegalStateException e) {
//            Errors errors = null;
//            errors.rejectValue(invalidId);
        }
        return "redirect:/";
    }

    @GetMapping("/user/userList") // 회원 목록
    public String userListForm(Model model) {
        model.addAttribute("userList", new UserListForm());
        return "user/UserList";
    }

    @PostMapping("/user/userList")
    public String userList(@ModelAttribute UserListForm form, Model model) {
        log.info("\nstudent = {}\nteacher = {}", form.getStudent(), form.getTeacher());

        int check_bool = 0;
        List<User> findList;

        if (form.getStudent()) {
            findList = userService.findStudentAll();
        } else if (form.getTeacher()) {
            findList = userService.findTeacherAll();
        } else {
            findList = userService.findUserAll();
        }
        if (!findList.isEmpty()) {
            model.addAttribute("users", findList);
        } else {
            User user = User.builder()
                    .name(null)
                    .loginId(null)
                    .loginPw(null)
                    .schoolCategory(null)
                    .regDate(null)
                    .build();
            model.addAttribute("users", user);
        }
        model.addAttribute("userList", new UserListForm());
        return "user/UserList";
    }
}
