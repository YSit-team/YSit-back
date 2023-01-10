package YSIT.YSit.board.article.dto;

import YSIT.YSit.board.Board;
import YSIT.YSit.board.article.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleForm {
    private Long id;
    private String title;
    private String body;
    private Board category;
    private ArticleStatus status;
    private LocalDateTime regDate;
    public ArticleForm() {
    }
}
