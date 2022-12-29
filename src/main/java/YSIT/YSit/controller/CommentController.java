package YSIT.YSit.controller;

import YSIT.YSit.domain.Article;
import YSIT.YSit.domain.Comment;
import YSIT.YSit.domain.User;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final UserService userService;
    private final EntityManager em;
    @PostMapping("/comment/write")
    public String writeComment(HttpServletRequest request,
                             @ModelAttribute CommentForm form,
                             BindingResult result, Model model) {
        if (form.getBody().isEmpty()) {
            result.rejectValue("body", "required");

            return returnPage(form.getArticleId(), model);
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);
        Long maxRef = commentService.getMaxRef();

        maxRef += 1;
        Comment comment = Comment.builder()
                .ref(maxRef)
                .articleId(form.getArticleId())
                .writeUser(user.getLoginId())
                .body(form.getBody())
                .build();
        commentService.save(comment);

        return returnPage(form.getArticleId(), model);
    }

    @PostMapping("/comment/nestedReply")
    public String nestedReply(@ModelAttribute CommentForm form,
                              BindingResult result,
                              HttpServletRequest request,
                              Model model) {
        if (form.getBody().isEmpty()) {
            result.rejectValue("body", "required");
            return returnPage(form.getArticleId(), model);
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);
        Comment parentCom = commentService.findOne(form.getParentId());

        Comment refOrderEmptyCheck = commentService.findByRefOrder(parentCom.getRefOrder() + 1);
        if (!Objects.isNull(refOrderEmptyCheck)) {
            Long plussedRefOrder = refOrderEmptyCheck.getRefOrder() + 1;
            refOrderEmptyCheck.changeRefOrder(plussedRefOrder);
            while (!Objects.isNull(commentService.findByRefOrder(plussedRefOrder))) {
                Comment loopEmptyCheck = commentService.findByRefOrder(plussedRefOrder);
                plussedRefOrder += 1;
                loopEmptyCheck.changeRefOrder(plussedRefOrder);
            }
        }

        Comment comment = Comment.builder()
                .articleId(form.getArticleId())
                .writeUser(user.getLoginId())
                .parentId(form.getParentId()) //
                .body(form.getBody())
                .ref(parentCom.getRef()) //
                .step(parentCom.getStep() + 1) //
                .refOrder(parentCom.getRefOrder() + 1) //
                .build();
        commentService.save(comment);

        return returnPage(form.getArticleId(), model);
    }

    @PostMapping("/comment/delComment")
    public String delComment(@ModelAttribute CommentForm form,
                             Model model) {
        commentService.delComment(form.getId());
        em.flush();
        return returnPage(form.getArticleId(), model);
    }

    public String returnPage(Long articleId, Model model) {
        Article article = articleService.findOne(articleId);
        List<Comment> comments = commentService.findByArt(articleId);
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("comments", comments);
        return "redirect:/article/articlePage/" + articleId.toString() + "/view";
    }
}