package YSIT.YSit.board.comment.entity;

import YSIT.YSit.board.article.entity.Article;
import YSIT.YSit.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name = "comment_id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String writeUser;
    private String body;
    private LocalDateTime regDate;
    private Long ref; // 그룹
    private Long step; // 그룹 내 단계
    private Long refOrder; // 그룹 내 순서
    private String parentId; // 부모 ID

    @Builder
    public Comment(String id, String articleId, User user, String body, Long ref, Long step, Long refOrder, String parentId, LocalDateTime regDate) {
        if (step == null) step = 0L;
        if (refOrder == null) refOrder = 0L;
        if (parentId == null) parentId = null;
        if (regDate == null) regDate = LocalDateTime.now();
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }

        this.id = id;
        this.articleId = articleId;
        this.body = body;
        this.user = user;
        this.writeUser = user.getLoginId();
        this.ref = ref;
        this.step = step;
        this.refOrder = refOrder;
        this.parentId = parentId;
        this.regDate = regDate;
    }

    public void changeRefOrder(Long refOrder) {
        this.refOrder = refOrder;
    }
    public void changeBody (String body) {
        this.body = body;
    }
}
