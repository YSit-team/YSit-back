package YSIT.YSit.videotools.dto;

import YSIT.YSit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.context.TenantIdentifierMismatchException;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RentalForm {

    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private List<String> rentalTools;
    private String uploader;
    private String schoolNumber;
    private String headCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime rentalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime returnDate;
    private String reason;
    @Builder
    public RentalForm(
            List<String> rentalTools,
            String agent, String schoolNumber, String headCount,
            LocalDateTime rentalDate, LocalDateTime returnDate,
//            long phoneNum,
            String reason, User uploader
    ) {
        this.rentalTools = rentalTools;
        this.uploader = agent;
        this.headCount = headCount;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.reason = reason;
    }
}
