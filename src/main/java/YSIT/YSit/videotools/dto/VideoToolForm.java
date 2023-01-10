package YSIT.YSit.videotools.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoToolForm {
    private Long id;
    private String name;
    private int quantity;
    public VideoToolForm() {
    }
}
