package YSIT.YSit.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleListForm {

    private Boolean title;
    private Boolean body;
    private Boolean myPage;
    private String search;
    public ArticleListForm() {
    }
}
