package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Article.class)
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "student_id")
    private User student;

    private String cbody;
    private LocalDateTime cDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @OneToMany(mappedBy = "parent")
    @Column(name = "child_id")
    private List<Comment> child = new ArrayList<>();

    public void addChildComment(Comment child) {
        this.child.add(child);
        child.addParentComment(this);
    }
    private void addParentComment(Comment parent) {
        this.parent = parent;
    }
}
