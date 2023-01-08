package YSIT.YSit.controller.form;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleUpdateForm {
    @Size(max = 50)
    private String title;
    private String body;
    private Boolean status;
}
