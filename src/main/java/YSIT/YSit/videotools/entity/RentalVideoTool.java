package YSIT.YSit.videotools.entity;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.videotools.RentalStatus;
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = VideoTool.class)
    @JoinColumn(name = "videoTool_id")
    private VideoTool videoTool;

    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private LocalDateTime regDate;
    private String body;
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private Long ref;
}
