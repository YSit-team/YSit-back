package YSIT.YSit.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserForm {
    private Long id;
    private String LoginId;
    private String LoginPw;
    private String name;

    private LocalDateTime regDate;
    private Boolean rank;
    public UserForm(){
    }
}