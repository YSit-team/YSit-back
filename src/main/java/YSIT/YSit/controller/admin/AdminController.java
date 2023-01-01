package YSIT.YSit.controller.admin;

import YSIT.YSit.controller.form.AdminForm;
import YSIT.YSit.controller.form.UserForm;
import YSIT.YSit.controller.form.UserListForm;
import YSIT.YSit.domain.Admins;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.AdminService;
import YSIT.YSit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/admin/login")
    public String loginForm(Model model) {
        model.addAttribute("adminForm", new AdminForm());
        return "/admins/admin/Login";
    }

    @PostMapping("/admin/login")
    public String login(@ModelAttribute AdminForm form, BindingResult result,
                        HttpServletRequest request) {
        if (form.getLoginCode().isEmpty()) {
            result.rejectValue("loginCode", "required");
            return "/admins/admin/Login";
        }
        List<Admins> validAdmin = adminService.findByLoginCode(form.getLoginCode());
        if (validAdmin.isEmpty()) {
            result.rejectValue("loginCode", "validCode");
            return "/admins/admin/Login";
        }
        Admins admin = validAdmin.get(0);
        adminService.save(admin);

        HttpSession session = request.getSession();
        session.setAttribute("adminId", admin.getId());

        return "redirect:/";
    }



    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }
}