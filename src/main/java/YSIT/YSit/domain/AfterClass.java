package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class AfterClass {
    @GeneratedValue @Id
    @Column(name = "afterClass_id")
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "afterClass", cascade = CascadeType.ALL)
    private List<AfterClassStudent> afterClassStudents = new ArrayList<>();

    private LocalDateTime regDate;
}
