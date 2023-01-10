package YSIT.YSit.board.article.entity;

import YSIT.YSit.board.Board;
import YSIT.YSit.board.article.ArticleStatus;
import YSIT.YSit.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Article {
    @GeneratedValue @Id
    @Column(name = "article_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Board category;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private Long viewCnt;
    @Enumerated(EnumType.STRING)
    private ArticleStatus status; // PUBLIC, PRIVATE
    private String writeUser;
    private String title;
    private String body;
    private LocalDateTime regDate;

    @Builder
    public Article (Long id, String title, String body, Board category,
                    User user, ArticleStatus status, LocalDateTime regDate,
                    Long viewCnt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.user = user;
        this.writeUser = user.getLoginId();
        this.status = status;
        this.regDate = regDate;
        if (viewCnt == null) viewCnt = 0L;
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
