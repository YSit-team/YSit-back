package YSIT.YSit.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserForm {
    private Long id;
    @NotBlank
    private String LoginId;
    @NotBlank
    private String LoginPw;
    @NotBlank
    private String name;

    private LocalDateTime regDate;
    private Boolean rank;
    public UserForm(){
    }
}