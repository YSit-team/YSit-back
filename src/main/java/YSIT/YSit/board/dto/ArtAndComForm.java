package YSIT.YSit.board.dto;

import YSIT.YSit.board.Board;
import YSIT.YSit.board.article.ArticleStatus;
import YSIT.YSit.board.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class ArtAndComForm {
    private String title;
    private String body;
    private Board category;
    private ArticleStatus status;
    private String writeUser;
    private Long viewCnt;
    private LocalDateTime regDate;
    private List<Comment> comments;

    @Builder
    public ArtAndComForm(String title, String body, Board category, ArticleStatus status, String writeUser, LocalDateTime regDate, List<Comment> comments, Long viewCnt) {
        this.title = title;
        this.body = body;
        this.viewCnt = viewCnt;
        this.category = category;
        this.status = status;
        this.writeUser = writeUser;
        this.regDate = regDate;
        this.comments = comments;
    }
}
