package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
public class RentalVideotool {
    @Id @GeneratedValue
    @Column(name = "rentalvt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Videotool.class)
    @JoinColumn(name = "videotool_id")
    private Videotool videotool;

    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private LocalDateTime regDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    private AppStatus appStatus;
}
