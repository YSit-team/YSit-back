package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Counsel {
    @Id @GeneratedValue
    @Column(name = "counsel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime regDate;
}
