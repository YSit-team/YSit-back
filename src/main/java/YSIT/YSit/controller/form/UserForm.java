package YSIT.YSit.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserForm {
    private Long id;
    private String loginId;
    private String loginPw;
    private String name;
    private LocalDateTime regDate;
    private Boolean rank;
    @Builder
    public UserForm(String loginId, String loginPw, String name, LocalDateTime regDate){
        if (regDate == null) regDate = LocalDateTime.now();
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.regDate = regDate;
    }
}