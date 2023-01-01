package YSIT.YSit.controller.user;

import YSIT.YSit.controller.form.ArticleForm;
import YSIT.YSit.controller.form.ArticleListForm;
import YSIT.YSit.controller.form.ArticleUpdateForm;
import YSIT.YSit.controller.form.CommentForm;
import YSIT.YSit.domain.*;
import YSIT.YSit.repository.ArticleRepository;
import YSIT.YSit.service.ArticleService;
import YSIT.YSit.service.CommentService;
import YSIT.YSit.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    private final CommentService commentService;

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
        return "users/article/Write";
    }

    @PostMapping("/article/write")
    public String write(@ModelAttribute ArticleForm form, BindingResult result,
                        HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);

        model.addAttribute("loginId", user.getLoginId());
        if (Objects.isNull(session.getAttribute("Id"))){
            return "redirect:/";
        }
        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            result.rejectValue("title", "required");
        }
        if (form.getBody().isEmpty() || form.getBody() == null) {
            result.rejectValue("body", "required");
        }
        if (form.getCategory() == null) {
            result.rejectValue("category", "required");
        } else if (form.getCategory() == Board.공지 && user.getSchoolCategory() == SchoolCategory.STUDENT) {
            result.rejectValue("category", "studentCantSetNotice");
        }
        if (result.hasErrors()) {
            return "users/article/write";
        }
        if (!articleRepository.findByTitle(form.getTitle()).isEmpty()) {
            result.rejectValue("title", "sameTitle");
            return "users/article/write";
        }

        ArticleStatus articleStatus;
        if (form.getStatus()) {
            articleStatus = ArticleStatus.PRIVATE;
        } else {
            articleStatus = ArticleStatus.PUBLIC;
        }
        User intoArt = userService.findOne(id);
        Article article = Article.builder()
                .title(form.getTitle())
                .body(form.getBody())
                .status(articleStatus)
                .category(form.getCategory())
                .user(intoArt)
                .regDate(LocalDateTime.now())
                .build();
        articleService.save(article);

        return "redirect:/";
    }

    @GetMapping("/article/articleList")
    public String articleListForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long)session.getAttribute("Id");
        User user = userService.findOne(id);
        List<Article> articles = articleService.findAll();

        model.addAttribute("modal", false);
        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        model.addAttribute("articleList", new ArticleListForm());

        return "users/article/ArticleList";
    }

    @PostMapping("/article/articleList")
    public String articleList(@ModelAttribute ArticleListForm form, Model model,
                              HttpServletRequest request) {
        int nullCheck = 0;
        List<Article> findList = null;
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);

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
            findList = articleService.findByWriteUser(user.getLoginId());
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
        model.addAttribute("modal", false);
        model.addAttribute("articleList", new ArticleListForm());
        model.addAttribute("user", user);
        return "users/article/articleList";
    }

    @GetMapping("/article/articlePage/{articleId}/view")
    public String articlePageForm(@PathVariable("articleId") Long articleId, Model model,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);
        Article article = articleService.findOne(articleId);

        if (article.getStatus() == ArticleStatus.PRIVATE) {
            if (user.getSchoolCategory() == SchoolCategory.STUDENT) {
                if (user.getLoginId() != article.getWriteUser() && !Objects.isNull(article.getUser())) {
                    List<Article> articles = articleService.findAll();
                    model.addAttribute("articles", articles);
                    model.addAttribute("modal", true);
                    model.addAttribute("articleList", new ArticleListForm());
                    model.addAttribute("user", user);
                    return "users/article/ArticleList";
                }
            }
        }

        List<Comment> comments = commentService.findByArt(articleId);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        return "users/article/ArticlePage";
    }

    @GetMapping("/article/articlePage/{articleId}/update")
    public String articleUpdateForm(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.findOne(articleId);
        ArticleUpdateForm form = ArticleUpdateForm.builder()
                .id(articleId)
                .originTitle(article.getTitle())
                .originBody(article.getBody())
                .build();
        model.addAttribute("articleUpdateForm", form);
        model.addAttribute("articleId", articleId);
        return "users/article/ArticleUpdate";
    }

    @PostMapping("/article/articlePage/{articleUpdateFormId}/update")
    public String articleUpdate(@PathVariable("articleUpdateFormId") Long articleId,
                                @Valid @ModelAttribute ArticleUpdateForm form,
                                BindingResult result,
                                HttpServletRequest request,
                                Model model) {
        List<Article> compareArt = articleService.findByTitle(form.getUpdateTitle());
        if (!compareArt.isEmpty()) {
            result.rejectValue("updateTitle", "sameTitle");
            return "users/article/ArticleUpdate";
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);

        Article checkArt = articleService.findOne(articleId);
        if (!checkArt.getWriteUser().equals(user.getLoginId())) {
            return "redirect:/";
        }

        ArticleStatus status;
        if (form.getStatus()) {
            status = ArticleStatus.PRIVATE;
        } else {
            status = ArticleStatus.PUBLIC;
        }

        Article article = Article.builder()
                .id(articleId)
                .title(form.getUpdateTitle())
                .body(form.getUpdateBody())
                .status(status)
                .build();
        articleService.updateArticle(article);

        return "redirect:/article/articlePage/" + article.getId().toString() + "/view";
    }
}

