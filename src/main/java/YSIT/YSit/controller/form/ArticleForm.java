package YSIT.YSit.controller.form;

import YSIT.YSit.domain.ArticleStatus;
import YSIT.YSit.domain.Board;
import YSIT.YSit.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

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
