package YSIT.YSit.videotools.entity;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.videotools.RentalStatus;
import YSIT.YSit.videotools.dto.RentalListForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Rental {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Id
    @Column(name = "rentalVt_id")
    private String id;
    private String headCnt; // 인원
    private List<String> rentalTools;
    private String uploader;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private String rentalDate; // 대여일
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private String returnDate; // 반납일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate; // 작성일
    private String reason;
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
//    private long phoneNum;
    @Builder
    private Rental(String id, String headCnt, String uploader, List<String> rentalTools,
                   LocalDateTime regDate, String rentalDate, String returnDate,
                   String reason, RentalStatus status
//            , long phoneNum
            ) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }
        this.id = id;
        this.rentalTools = rentalTools;
        this.uploader = uploader;
        this.headCnt = headCnt;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.regDate = regDate;
        this.reason = reason;
        this.status = status;
//        this.phoneNum = phoneNum;
    }

    public void changeStatus(RentalStatus status) {
        if (status == null) {
            throw new IllegalStateException("status is null");
        }
        this.status = status;
    }
}
