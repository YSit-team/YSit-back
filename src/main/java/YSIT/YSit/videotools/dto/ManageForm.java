package YSIT.YSit.videotools.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManageForm {
    private Boolean manage;
    private String rentId;
    @Builder
    public ManageForm(Boolean manage, String rentId) {
        this.manage = manage;
        this.rentId = rentId;
    }
}
