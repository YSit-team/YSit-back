package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class RentalVideoTool {
    @Id @GeneratedValue
    @Column(name = "rentalVt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = VideoTool.class)
    @JoinColumn(name = "videoTool_id")
    private VideoTool videoTool;

    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private LocalDateTime regDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    private AppStatus appStatus;

    private Long ref;
}
