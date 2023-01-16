package YSIT.YSit.board.article.dto;

import YSIT.YSit.board.article.Board;
import YSIT.YSit.board.article.ArticleStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleUpdateForm {
    @Size(max = 50)
    private String title;
    private String body;
    private Board category;
    private ArticleStatus status;
}
