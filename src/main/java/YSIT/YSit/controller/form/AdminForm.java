package YSIT.YSit.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class AdminForm {
    private Long id;
    private String loginCode;
    private String name;
    public AdminForm() {
    }
}
