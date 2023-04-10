package YSIT.YSit.videotools.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalListForm {
    private String toolName;
    private int rentalQuantity;

    @Builder
    public RentalListForm(String toolName, int rentalQuantity) {
        this.toolName = toolName;
        this.rentalQuantity = rentalQuantity;
    }
}
