package YSIT.YSit.controller;

import YSIT.YSit.domain.Article;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.ArticleRepository;
import YSIT.YSit.service.ArticleService;
import YSIT.YSit.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final EntityManager em;

    @GetMapping("/article/write")
    public String writeForm(Model model,
                              @CookieValue(name = "Id", required = false) Long Id) {
        if (Id == null) {
            return "redirect:/";
        }
        User user = userService.findOne(Id);
        model.addAttribute("loginId", user.getLoginId());
        model.addAttribute("articleForm", new ArticleForm());
        return "/article/Write";
    }

    @PostMapping("/article/write")
    public String write(@ModelAttribute ArticleForm form, BindingResult result,
                        @CookieValue(name = "Id") Long Id) {

        if (!articleRepository.findByTitle(form.getTitle()).isEmpty()) {
            result.rejectValue("title", "sameTitle");
        }
        if (form.getTitle().isBlank()) {
            result.rejectValue("title", "required");
        }
        if (form.getBody().isBlank()) {
            result.rejectValue("body", "required");
        }
        if (form.getCategory() == null) {
            result.rejectValue("category", "required");
        }
        if (result.hasErrors()) {
            return "/article/write";
        }

        Article article = Article.builder()
                .title(form.getTitle())
                .body(form.getBody())
                .status(form.getStatus())
                .user(userService.findOne(Id))
                .build();
        articleService.save(article);

        return "redirect:/";
    }
}
