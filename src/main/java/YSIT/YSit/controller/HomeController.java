package YSIT.YSit.controller;

import YSIT.YSit.domain.Admins;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.AdminService;
import YSIT.YSit.service.UserService;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

//    @GetMapping("/")
//    public String home(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession();
//        Long userId = (Long) session.getAttribute("Id");
//        Long adminId = (Long) session.getAttribute("adminId");
//        if (!Objects.isNull(userId)) {
//            User user = userService.findOne(userId);
//            model.addAttribute("user", user);
//            return "users/LoginUserHome";
//        } else if (!Objects.isNull(adminId)){
//            Admins admin = adminService.findOne(adminId);
//            model.addAttribute("admin", admin);
//            return "admins/LoginAdminHome";
//        } else {
//            return "Home";
//        }
//    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/home")
    public String home(){
        return "React & Spring connection Successful";
    }
}