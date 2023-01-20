package YSIT.YSit.videotools.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoToolForm {
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    private static final String TIME_PATTERN = "hh:mm";
    private Long id;
    private String name;
    private int use;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime rentalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime returnDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime bringTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime returnTime;
    private int quantity;
//    private int
    public VideoToolForm() {
    }
}
