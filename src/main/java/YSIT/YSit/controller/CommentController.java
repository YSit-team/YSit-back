package YSIT.YSit.controller;

import YSIT.YSit.domain.Article;
import YSIT.YSit.domain.Comment;
import YSIT.YSit.domain.User;
import YSIT.YSit.service.ArticleService;
import YSIT.YSit.service.CommentService;
import YSIT.YSit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final UserService userService;
    @PostMapping("/comment/write")
    public String writeComment(HttpServletRequest request,
                             @ModelAttribute CommentForm form,
                             BindingResult result, Model model) {
        if (form.getBody().isEmpty()) {
            result.rejectValue("body", "required");

            Article article = articleService.findOne(form.getArticleId());
            List<Comment> comments = commentService.findByArt(article.getId());
            model.addAttribute("article", article);
            model.addAttribute("commentForm", new CommentForm());
            model.addAttribute("comments", comments);
            return "article/ArticlePage";
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);
        Comment comment = Comment.builder()
                .ref(commentService.getMaxRef() + 1L)
                .articleId(form.getArticleId())
                .writeUser(user.getLoginId())
                .body(form.getBody())
                .build();
        commentService.save(comment);
        log.info("ArticleID = {}", form.getArticleId());
        Article article = articleService.findOne(form.getArticleId());
        List<Comment> comments = commentService.findByArt(form.getArticleId());
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("comments", comments);

        return "article/ArticlePage";
    }
}
