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

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/home")
    public String home(){
        return "React & Spring connection Successful";
    }
}