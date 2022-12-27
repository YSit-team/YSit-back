package YSIT.YSit.controller;

import YSIT.YSit.domain.Article;
import YSIT.YSit.domain.ArticleStatus;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.ArticleRepository;
import YSIT.YSit.service.ArticleService;
import YSIT.YSit.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final EntityManager em;

    @GetMapping("/article/write")
    public String writeForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        if (Objects.isNull(session.getAttribute("Id"))){
            return "redirect:/";
        }
        User user = userService.findOne(id);
        model.addAttribute("loginId", user.getLoginId());
        model.addAttribute("articleForm", new ArticleForm());
        return "/article/Write";
    }

    @PostMapping("/article/write")
    public String write(@ModelAttribute ArticleForm form, BindingResult result,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (Objects.isNull(session.getAttribute("Id"))){
            return "redirect:/";
        }
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

        Long id = (Long) session.getAttribute("Id");

        ArticleStatus articleStatus;
        if (form.getStatus()) {
            articleStatus = ArticleStatus.PRIVATE;
        } else {
            articleStatus = ArticleStatus.PUBLIC;
        }
        User user = userService.findOne(id);
        Article article = Article.builder()
                .title(form.getTitle())
                .body(form.getBody())
                .status(articleStatus)
                .category(form.getCategory())
                .user(user)
                .regDate(LocalDateTime.now())
                .build();
        articleService.save(article);

        return "redirect:/";
    }

    @GetMapping("/article/articleList")
    public String articleListForm(Model model) {
        model.addAttribute("articleList", new ArticleListForm());
        return "article/ArticleList";
    }

    @PostMapping("/article/articleList")
    public String articleList(@ModelAttribute ArticleListForm form, Model model) {
        int nullCheck = 0;
        List<Article> findList = null;

        if (form.getTitle()) {
            nullCheck += 1;
            findList = articleService.findByTitle(form.getSearch());
        }
        if (form.getBody()) {
            nullCheck += 1;
            findList = articleService.findByBody(form.getSearch());
        }
        if (nullCheck >= 2 || nullCheck <= 0) {
             findList = articleService.findAll();
        }

        if (findList != null) {
            model.addAttribute("articles", findList);
        } else {
            Article article = Article.builder()
                    .title(null)
                    .body(null)
                    .status(null)
                    .user(null)
                    .regDate(null)
                    .build();
            model.addAttribute("articles", article);
        }
        model.addAttribute("articleList", new ArticleListForm());
        return "article/articleList";
    }

    @GetMapping("/article/articlePage/{articleId}/view")
    public String articlePageForm(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.findOne(articleId);
        model.addAttribute("article", article);
        return "article/ArticlePage";
    }
}

