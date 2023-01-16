package YSIT.YSit.board.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentForm {
    private String id;
    private Long ref;
    private Long step;
    private Long refOrder;
    private String parentId;
    private String userId;
    private String writeUser;
    private String articleId;
    private String body;
    private LocalDateTime regDate;

    public CommentForm() {}
}
