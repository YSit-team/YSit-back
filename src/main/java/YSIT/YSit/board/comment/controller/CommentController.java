package YSIT.YSit.board.comment.controller;

import YSIT.YSit.board.article.service.ArticleService;
import YSIT.YSit.board.comment.dto.CommentForm;
import YSIT.YSit.board.comment.service.CommentService;
import YSIT.YSit.board.comment.entity.Comment;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/coms")
    public ResponseEntity parentIsNull_CommentWrite(HttpServletRequest request,
                                       @ModelAttribute CommentForm form) {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("Id");

        User user = userService.findOne(userId);
        if (form.getParentId() == null) {

            Long maxRef = commentService.getMaxRef();
            maxRef += 1;
            Comment comment = Comment.builder()
                    .ref(maxRef)
                    .articleId(form.getArticleId())
                    .user(user)
                    .body(form.getBody())
                    .build();
            commentService.save(comment);

            Comment responseComment = commentService.findOne(comment.getId());
            return ResponseEntity.status(HttpStatus.OK).body(responseComment);

        } else {

            Comment parentCom = commentService.findOne(form.getParentId());
            commentService.addRefOrderForCommentRef(parentCom.getRef(), parentCom.getRefOrder() + 1);

            Comment comment = Comment.builder()
                    .articleId(form.getArticleId())
                    .user(user)
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
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@ModelAttribute CommentForm form) {
        Comment comment = commentService.findOne(form.getId());
        commentService.delCommentByUser(comment);

        return ResponseEntity.status(HttpStatus.OK).body("삭제되었습니다");
    }
}