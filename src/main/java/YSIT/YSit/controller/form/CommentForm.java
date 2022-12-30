package YSIT.YSit.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentForm {
    private Long id;
    private Long ref;
    private Long step;
    private Long refOrder;
    private Long parentId;
    private Long userId;
    private String writeUser;
    private Long articleId;
    private String body;
    private LocalDateTime regDate;

    public CommentForm() {}
}
