package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private Long articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String writeUser;
    private String body;
    private LocalDateTime regDate;
    private Long ref;
    private Long step;
    private Long refOrder;
    private Long parentId;

    @Builder
    public Comment(Long id, Long articleId, String writeUser, String body, Long ref, Long step, Long refOrder, Long parentId, LocalDateTime regDate) {
        if (step == null) step = 0L;
        if (refOrder == null) refOrder = 0L;
        if (parentId == null) parentId = 0L;
        if (regDate == null) regDate = LocalDateTime.now();

        this.id = id;
        this.articleId = articleId;
        this.writeUser = writeUser;
        this.body = body;
        this.ref = ref;
        this.step = step;
        this.refOrder = refOrder;
        this.parentId = parentId;
        this.regDate = regDate;
    }
}
