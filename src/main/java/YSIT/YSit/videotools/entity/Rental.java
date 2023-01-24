package YSIT.YSit.videotools.entity;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.videotools.RentalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Rental {

    private static final String DATE_PATTERN = "yyyy/MM/dd";
    private static final String TIME_PATTERN = "hh:mm";
    @Id @GeneratedValue
    @Column(name = "rentalVt_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private String writer; // 작성자
    private int headCnt; // 인원

    private List<String> videoTools;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime rentalDate; // 대여일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime returnDate; // 반납일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime rentalTime; // 대여시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime returnTime; // 반납시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate; // 작성일
    private String reason;
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @Builder
    private Rental(String id, User user, int headCnt, List<String> videoTools,
                   LocalDateTime regDate, LocalDateTime rentalDate, LocalDateTime returnDate,
                   LocalDateTime rentalTime, LocalDateTime returnTime, String reason, RentalStatus status) {
        if (id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }
        this.id = id;
        this.user = user;
        this.writer = user.getId();
        this.headCnt = headCnt;
//        this.videoTools = videoTools;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalTime = rentalTime;
        this.returnTime = returnTime;
        this.regDate = regDate;
        this.reason = reason;
        this.status = status;
    }

    public void changeStatus(RentalStatus status) {
        if (status == null) {
            throw new IllegalStateException("status is null");
        }
        this.status = status;
    }
}
