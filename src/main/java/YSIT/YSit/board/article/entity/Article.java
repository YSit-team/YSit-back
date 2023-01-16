package YSIT.YSit.board.article.entity;

import YSIT.YSit.board.article.Board;
import YSIT.YSit.board.article.ArticleStatus;
import YSIT.YSit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Article {
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    @Id
    @Column(name = "article_id")
    private String id;

    @Enumerated(EnumType.STRING)
    private Board category;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private Long viewCnt;
    @Enumerated(EnumType.STRING)
    private ArticleStatus status; // PUBLIC, PRIVATE
    private String title;
    private String body;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @Builder
    public Article (String id, String title, String body, Board category,
                    User user, ArticleStatus status, LocalDateTime regDate,
                    Long viewCnt) {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }
        if (viewCnt == null) viewCnt = 0L;

        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.user = user;
        this.status = status;
        this.regDate = regDate;
        this.viewCnt = viewCnt;
    }

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeBody(String body) {
        this.body = body;
    }
    public void changeStatus(ArticleStatus status) {
        this.status = status;
    }
    public void addViewCnt(){
        this.viewCnt += 1;
    }
}
