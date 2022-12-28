package YSIT.YSit.controller;

import YSIT.YSit.domain.SchoolCategory;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.UserRepository;
import YSIT.YSit.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final EntityManager em;

    @GetMapping("/user/register") // 회원가입 진입
    public String registerForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/Register";
    }

    @PostMapping("/user/register") // 회원가입 기능
    public String register(@Valid @ModelAttribute UserForm form, BindingResult result) {
        List<User> tempUser = userService.doublecheckLoginId(form.getLoginId());
        if(!tempUser.isEmpty()){
            result.rejectValue("loginId", "sameId");
            return "/user/Register";
        }
        if(form.getLoginId().isBlank()){
            result.rejectValue("loginId", "required");
        }
        if(form.getLoginPw().isBlank()) {
            result.rejectValue("loginPw", "required");
        }
        if(form.getName().isBlank()){
            result.rejectValue("name","required");
        }
        if (result.hasErrors()) {
            return "/user/Register";
        }

        SchoolCategory schoolCategory;

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
    public String login(@ModelAttribute UserForm form, BindingResult result,
                        HttpServletRequest request) {
        if(form.getLoginId().isBlank()){
            result.rejectValue("loginId", "required");
            return "user/Login";
        }
        if(form.getLoginPw().isBlank()){
            result.rejectValue("loginPw", "required");
            return "user/Login";
        }

        List<User> matchLogins = userService.matchLogins(form.getLoginId(), form.getLoginPw());
        if (matchLogins.isEmpty()) {
            result.rejectValue("loginPw", "validLogin");

            return "user/Login";
        }

        User user = matchLogins.get(0);

        HttpSession session = request.getSession();
        session.setAttribute("Id", user.getId());

        return "redirect:/";
    }

    public class SessionConst {
        public static final String LOGIN_USER = "loginUser";
    };

    @GetMapping("/user/userList") // 회원 목록
    public String userListForm(Model model) {
        model.addAttribute("userList", new UserListForm());
        return "user/UserList";
    }

    @PostMapping("/user/userList")
    public String userList(@ModelAttribute UserListForm form, Model model) {
        int check_bool = 0;
        List<User> findList = null;

        if (form.getStudent()) {
            findList = userService.findStudentAll();
            check_bool += 1;
        }
        if (form.getTeacher()) {
            findList = userService.findTeacherAll();
            check_bool += 1;
        }
        if (check_bool >= 2 || check_bool <= 0) {
            findList = userService.findUserAll();
        }

        if (findList != null) {
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

    @GetMapping("/user/userUpdate")
    public String updatePage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (Objects.isNull(session.getAttribute("Id"))){
            return "redirect:/";
        }
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);
        model.addAttribute("loginId", user.getLoginId());
        model.addAttribute("updateForm", new UserForm());
        return "user/UserUpdate";
    }
    @PostMapping("/user/userUpdate")
    public String userUpdate(@ModelAttribute UserForm form, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        User user2 = User.builder()
                .id(id)
                .name(form.getName())
                .loginId(form.getLoginId())
                .loginPw(form.getLoginPw())
                .build();
        userService.updateUser(user2);

        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logoutPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }
}
