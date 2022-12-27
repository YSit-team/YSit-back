package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
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

    private String title;
    private String body;
    private LocalDateTime regDate;

    @Builder
    public Article (String title, String body, Board category, User user, ArticleStatus status) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.user = user;
        this.status = status;
    }
}
