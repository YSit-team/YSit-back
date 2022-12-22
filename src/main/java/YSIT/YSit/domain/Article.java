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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Board.class)
    @JoinColumn(name = "category")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status; // PUBLIC, PRIVATE

    private String title;
    private String body;
    private LocalDateTime regDate;
}
