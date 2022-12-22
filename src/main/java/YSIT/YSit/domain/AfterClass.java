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
    @Column(name = "afterclass_id")
    private Long id;

    @Column(name = "ac_name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "ac_status")
    private AppStatus status;

    @OneToMany(mappedBy = "afterClass", cascade = CascadeType.ALL)
    private List<AfterClassStudent> afterClassStudents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Admins.class)
    @JoinColumn(name = "admin_id")
    private Admins admins;

    private LocalDateTime regDate;
}
