package YSIT.YSit.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleListForm {

    private Boolean title;
    private Boolean body;
    private String search;
    public ArticleListForm() {
    }
}
