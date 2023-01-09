package YSIT.YSit.controller.user;

import YSIT.YSit.controller.form.CommentForm;
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
import org.hibernate.engine.spi.Resolution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public ResponseEntity parentIsNull_CommentWrite(HttpServletRequest request,
                                       @ModelAttribute CommentForm form) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");

        if (form.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("값이 없습니다");
        }

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

        Comment responseComment = commentService.findOne(comment.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PostMapping("/comment/nestedReply")
    public ResponseEntity parentIsExist(@ModelAttribute CommentForm form,
                                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");

        if (form.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("값이 없습니다");
        }

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

        Comment responseComment = commentService.findOne(comment.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PostMapping("/comment/delComment")
    public ResponseEntity delete(@ModelAttribute CommentForm form,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        Comment comment = commentService.findOne(form.getId());
        commentService.delCommentByUser(comment);

        Comment responseComment = commentService.findOne(form.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }
}