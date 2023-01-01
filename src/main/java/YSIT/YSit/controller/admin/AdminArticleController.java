package YSIT.YSit.controller.admin;

import YSIT.YSit.controller.form.ArticleForm;
import YSIT.YSit.controller.form.ArticleListForm;
import YSIT.YSit.controller.form.CommentForm;
import YSIT.YSit.domain.*;
import YSIT.YSit.repository.ArticleRepository;
import YSIT.YSit.service.AdminService;
import YSIT.YSit.service.ArticleService;
import YSIT.YSit.service.CommentService;
import YSIT.YSit.service.UserService;
import jakarta.persistence.EntityManager;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminArticleController {
    private final AdminService adminService;
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final EntityManager em;
    private final CommentService commentService;

    @GetMapping("/admin/article/write")
    public String writeForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long adminId = (Long) session.getAttribute("adminId");
        Admins admin = adminService.findOne(adminId);
        if (Objects.isNull(admin)){
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        model.addAttribute("articleForm", new ArticleForm());
        return "admins/article/Write";
    }
    @PostMapping("/admin/article/write")
    public String write(@ModelAttribute ArticleForm form, BindingResult result,
                        HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long adminId = (Long) session.getAttribute("adminId");
        Admins admin = adminService.findOne(adminId);
        model.addAttribute("admin", admin);

        if (Objects.isNull(admin)){
            return "redirect:/";
        }
        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            result.rejectValue("title", "required");
        }
        if (form.getBody().isEmpty() || form.getBody() == null) {
            result.rejectValue("body", "required");
        }
        if (result.hasErrors()) {
            return "admins/article/Write";
        }
        if (!articleRepository.findByTitle(form.getTitle()).isEmpty()) {
            result.rejectValue("title", "sameTitle");
            return "admins/article/Write";
        }

        ArticleStatus articleStatus;
        if (form.getStatus()) {
            articleStatus = ArticleStatus.PRIVATE;
        } else {
            articleStatus = ArticleStatus.PUBLIC;
        }

        Article article = Article.builder()
                .title(form.getTitle())
                .body(form.getBody())
                .status(articleStatus)
                .category(Board.공지)
                .adminName(admin.getName())
                .regDate(LocalDateTime.now())
                .build();
        articleService.save(article);

        return "redirect:/";
    }

    @GetMapping("/admin/article/articleList")
    public String articleListForm(Model model, HttpServletRequest request) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        model.addAttribute("articleList", new ArticleListForm());

        return "admins/article/List";
    }

    @PostMapping("/admin/article/articleList")
    public String articleList(@ModelAttribute ArticleListForm form, Model model,
                              HttpServletRequest request) {
        int nullCheck = 0;
        List<Article> findList = null;

        HttpSession session = request.getSession();
        Long adminId = (Long) session.getAttribute("adminId");
        Admins admin = adminService.findOne(adminId);

        if (form.getTitle()) {
            nullCheck += 1;
            findList = articleService.findByTitle(form.getSearch());
        }
        if (form.getBody()) {
            nullCheck += 1;
            findList = articleService.findByBody(form.getSearch());
        }
        if (form.getMyPage()) {
            nullCheck += 1;
            findList = articleService.findByWriteUser(admin.getName());
        }
        if (nullCheck >= 3 || nullCheck <= 0) {
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
        return "admins/article/List";
    }

    @GetMapping("/admin/article/articlePage/{articleId}/view")
    public String articlePageForm(@PathVariable("articleId") Long articleId, Model model,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long adminId = (Long) session.getAttribute("adminId");
        Admins admin = adminService.findOne(adminId);
        Article article = articleService.findOne(articleId);
        List<Comment> comments = commentService.findByArt(articleId);
        model.addAttribute("admin", admin);
        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        return "/admins/article/Page";
    }

    @GetMapping("/admin/articleDelete/{articleId}")
    public String articleDelete(@PathVariable("articleId") Long articleId) {
        articleService.deleteById(articleId);

        return "redirect:/admin/article/articleList";
    }
}
