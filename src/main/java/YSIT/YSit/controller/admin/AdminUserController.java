package YSIT.YSit.controller.admin;

import YSIT.YSit.controller.form.UserForm;
import YSIT.YSit.controller.form.UserListForm;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final UserService userService;
    private final EntityManager em;

    @GetMapping("/admin/userList")
    public String userListForm(Model model) {
        model.addAttribute("userList", new UserListForm());
        return "admins/user/UserList";
    }
    @PostMapping("/admin/userList")
    public String userList(@ModelAttribute UserListForm form,
                           Model model) {
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
        return "admins/user/UserList";
    }

    @GetMapping("/admin/userUpdate/{userId}")
    public String updateForm(Model model,
                             @PathVariable("userId") Long userId) {
        User user = userService.findOne(userId);
        if (Objects.isNull(user)){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("updateForm", new UserForm());
        return "admins/user/UserUpdate";
    }

    @PostMapping("/admin/userUpdate/{userId}")
    public String userUpdate(@ModelAttribute UserForm form,
                             @PathVariable("userId") Long userId) {
        User user2 = User.builder()
                .id(userId)
                .name(form.getName())
                .loginId(form.getLoginId())
                .loginPw(form.getLoginPw())
                .build();
        userService.updateUser(user2);

        return "redirect:/";
    }

    @GetMapping("/admin/userDelete/{userId}")
    public String userDelete(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);
        return "redirect:/admin/userList";
    }
}
