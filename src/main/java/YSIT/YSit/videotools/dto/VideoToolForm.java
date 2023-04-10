package YSIT.YSit.videotools.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class VideoToolForm {
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    private static final String TIME_PATTERN = "hh:mm";
    private String id;
    private String name;
    private int quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @Builder
    public VideoToolForm(String id, String name, int quantity, LocalDateTime regDate) {
        if (regDate == null) regDate = LocalDateTime.now();
        if (id == null) id = UUID.randomUUID().toString();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.regDate = regDate;
    }
}
