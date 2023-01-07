package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class AfterClassStudent {
    @GeneratedValue @Id
    @Column(name = "afterClass_student_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = AfterClass.class)
    @JoinColumn(name = "afterClass_id")
    private AfterClass afterClass;

    private String start_time;
    private String end_time;
    private LocalDateTime regDate;
}
