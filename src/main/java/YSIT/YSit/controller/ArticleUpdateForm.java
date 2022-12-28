package YSIT.YSit.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleUpdateForm {
    private Long id;
    private String originTitle;
    private String originBody;
    private String updateTitle;
    private String updateBody;
    private Boolean status;

    @Builder
    public ArticleUpdateForm(Long id, String originTitle, String originBody) {
        this.id = id;
        this.originTitle = originTitle;
        this.originBody = originBody;
    }
}
