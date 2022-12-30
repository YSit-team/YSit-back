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
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    @NotBlank
    private Board category;
    private Boolean status;
    private LocalDateTime regDate;
    public ArticleForm() {
    }
}
