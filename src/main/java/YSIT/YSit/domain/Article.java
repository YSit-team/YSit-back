package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

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

    @Enumerated(EnumType.STRING)
    private ArticleStatus status; // PUBLIC, PRIVATE
    private String writeUser;
    private String title;
    private String body;
    private LocalDateTime regDate;

    @Builder
    public Article (Long id, String title, String body, Board category, User user, ArticleStatus status, LocalDateTime regDate, String adminName) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.user = user;
        if (user != null) {
            this.writeUser = user.getLoginId();
        } else {
            this.writeUser = adminName;
        }
        this.status = status;
        this.regDate = regDate;
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
}
