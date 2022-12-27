package YSIT.YSit.controller;

import YSIT.YSit.domain.User;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (Objects.isNull(session.getAttribute("Id"))){
            return "Home";
        }
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);
        log.info("LoginID = {}", user.getLoginId());
        session.setAttribute("loginId", user.getLoginId());

        return "LoginHome";
    }
}
