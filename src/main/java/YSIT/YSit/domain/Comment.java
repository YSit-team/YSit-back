package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Article.class)
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "student_id")
    private User student;

    private String cBody;
    private LocalDateTime cDate;
    private Long group;
    private Long level;
    private Long groupOrder;
    private Long parentId;
}
