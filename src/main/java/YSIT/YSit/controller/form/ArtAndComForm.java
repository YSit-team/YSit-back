package YSIT.YSit.controller.form;

import YSIT.YSit.domain.ArticleStatus;
import YSIT.YSit.domain.Board;
import YSIT.YSit.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class ArtAndComForm {
    private String title;
    private String body;
    private Board category;
    private ArticleStatus status;
    private String writeUser;
    private LocalDateTime regDate;
    private List<Comment> comments;

    @Builder
    public ArtAndComForm(String title, String body, Board category, ArticleStatus status, String writeUser, LocalDateTime regDate, List<Comment> comments) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.status = status;
        this.writeUser = writeUser;
        this.regDate = regDate;
        this.comments = comments;
    }
}
