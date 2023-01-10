package YSIT.YSit.board.article.controller;

import YSIT.YSit.board.dto.ArtAndComForm;
import YSIT.YSit.board.Board;
import YSIT.YSit.board.article.*;
import YSIT.YSit.board.article.dto.ArticleForm;
import YSIT.YSit.board.article.dto.ArticleUpdateForm;
import YSIT.YSit.board.article.entity.Article;
import YSIT.YSit.board.article.service.ArticleService;
import YSIT.YSit.board.comment.entity.Comment;
import YSIT.YSit.board.comment.service.CommentService;
import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping("/write") // 작성
    public ResponseEntity write(@ModelAttribute ArticleForm form,
                        HttpServletRequest request) {
        log.info("성공");
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("Id");
        User user = userService.findOne(id);

        // 검증
        ResponseEntity response = userValid(request);
        if (response != null) {
            return response;
        }
        if (form.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목 값이 없습니다");
        }
        if (form.getBody().isEmpty()) {
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
        log.info("성공2");

        // 데이터 처리
        User intoArt = userService.findOne(id);
        Article article = Article.builder()
                .title(form.getTitle())
                .body(form.getBody())
                .status(form.getStatus())
                .category(form.getCategory())
                .user(intoArt)
                .regDate(LocalDateTime.now())
                .build();
        articleService.save(article);
        Article responseArt = articleService.findOne(article.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseArt);
    }

    @GetMapping("/articleList/{bool_title}/{bool_body}/{searchBody}")
    public ResponseEntity list(
            @PathVariable("bool_title") Boolean title,
            @PathVariable("bool_body") Boolean body,
            @PathVariable("searchBody") String searchBody,
            HttpServletRequest request) {

        log.info("\ntitle = {}\nbody = {}\nsearchBody = {}",title,body,searchBody);

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

    @GetMapping("/articlePage/{articleId}/view")
    public ResponseEntity view(@PathVariable("articleId") Long articleId,
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
                // 조회수 1 추가
                .viewCnt(viewArticle.getViewCnt() + 1)
                .comments(artInComment)
                .build();

        ResponseEntity response = userValid(request);
        if (response != null) {
            return response;
        }

        if (viewArticle.getStatus() == ArticleStatus.PRIVATE) {
            if (user.getLoginId() == viewArticle.getWriteUser() || user.getSchoolCategory() == SchoolCategory.TEACHER ) {
                // DB에 조회수 1 추가
                viewArticle.addViewCnt();
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("학생은 비공개 게시물을 볼 수 없습니다");
            }
        } else {
            // DB에 조회수 1 추가
            viewArticle.addViewCnt();
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
    }

    @PostMapping("/articlePage/{articleId}/update")
    public ResponseEntity update(@PathVariable("articleId") Long articleId,
                                @ModelAttribute ArticleUpdateForm form,
                                HttpServletRequest request) {
        log.info("성공");

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

        Article article = Article.builder()
                .id(articleId)
                .title(form.getTitle())
                .body(form.getBody())
                .user(user)
                .status(form.getStatus())
                .build();
        articleService.updateArticle(article);

        Article updatedArt = articleService.findOne(articleId);

        if (!updatedArt.getTitle().equals(form.getTitle())) {
            throw new IllegalStateException("업데이트 실패");
        }

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

