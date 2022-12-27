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
    public Article (String title, String body, Board category, User user, ArticleStatus status, LocalDateTime regDate) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.user = user;
        this.writeUser = user.getLoginId();
        this.status = status;
        this.regDate = regDate;
    }
}
