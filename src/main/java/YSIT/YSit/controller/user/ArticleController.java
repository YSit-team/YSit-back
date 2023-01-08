package YSIT.YSit.controller.user;

import YSIT.YSit.controller.form.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping("/article/write") // 작성
    public ResponseEntity write(@ModelAttribute ArticleForm form,
                        HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);

        // 검증
        ResponseEntity response = userValid(request);
        if (response != null) {
            return response;
        }
        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목 값이 없습니다");
        }
        if (form.getBody().isEmpty() || form.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("내용 값이 없습니다");
        }
        if (form.getCategory() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카테고리 값이 없습니다");
        } else if (form.getCategory() == Board.공지 && user.getSchoolCategory() == SchoolCategory.STUDENT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("학생은 공지를 작성할 수 없습니다");
        }
        if (form.getStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("공개여부 값이 없습니다");
        }
        if (!articleService.findByTitle(form.getTitle()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 같은 제목의 게시물이 있습니다");
        }

        // 데이터 처리
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
        Article responseArt = articleService.findOne(article.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseArt);
    }

    @GetMapping("/article/articleList/{bool_title}/{bool_body}/{searchBody}")
    public ResponseEntity articleListForm(
            @PathVariable("bool_title") Boolean title,
            @PathVariable("bool_body") Boolean body,
            @PathVariable("searchBody") String searchBody,
            HttpServletRequest request) {

        ResponseEntity response = userValid(request);
        if (response != null) {
            return response;
        }
        List<Article> findList;
        if (title && body || searchBody.isEmpty()) {
            findList = articleService.findAll();
        } else if (title) {
            findList = articleService.findByTitle(searchBody);
        } else if (body) {
            findList = articleService.findByBody(searchBody);
        } else {
            findList = articleService.findAll();
        }

        return ResponseEntity.status(HttpStatus.OK).body(findList);
    }

    @GetMapping("/article/articlePage/{articleId}/view")
    public ResponseEntity articlePageForm(@PathVariable("articleId") Long articleId,
                                  HttpServletRequest request) {
        Article viewArticle = articleService.findOne(articleId);
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);
        List<Comment> artInComment = commentService.findByArt(articleId);

        ArtAndComForm responseBody = ArtAndComForm.builder()
                .title(viewArticle.getTitle())
                .body(viewArticle.getBody())
                .category(viewArticle.getCategory())
                .status(viewArticle.getStatus())
                .writeUser(viewArticle.getWriteUser())
                .regDate(viewArticle.getRegDate())
                .comments(artInComment)
                .build();

        ResponseEntity response = userValid(request);
        if (response != null) {
            return response;
        }
        if (viewArticle.getStatus() == ArticleStatus.PRIVATE) {
            if (user.getLoginId() == viewArticle.getWriteUser() || user.getSchoolCategory() == SchoolCategory.TEACHER ) {
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("학생은 비공개 게시물을 볼 수 없습니다");
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
    }

    @PostMapping("/article/articlePage/{articleId}/update")
    public ResponseEntity articleUpdate(@PathVariable("articleId") Long articleId,
                                @Valid @ModelAttribute ArticleUpdateForm form,
                                HttpServletRequest request) {
        Article updateArt = articleService.findOne(articleId);
        List<Article> compareArt = articleService.findByTitle(form.getTitle());
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("Id");
        User user = userService.findOne(userId);

        // 검증
        if (!compareArt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 같은 제목의 게시물이 있습니다");
        }
        if (!updateArt.getWriteUser().equals(user.getLoginId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("게시물 작성자가 아닙니다");
        }

        // 처리
        ArticleStatus status;
        if (form.getStatus()) {
            status = ArticleStatus.PRIVATE;
        } else {
            status = ArticleStatus.PUBLIC;
        }

        Article article = Article.builder()
                .id(articleId)
                .title(form.getTitle())
                .body(form.getBody())
                .status(status)
                .build();
        articleService.updateArticle(article);
        Article updatedArt = articleService.findOne(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedArt);
    }
    public ResponseEntity userValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);
        if (Objects.isNull(user)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("작성자가 아니거나 로그인 상태가 아닙니다");
        } else {
            return null;
        }
    }
}

