package YSIT.YSit.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserForm {
    private Long id;
    @NotBlank(message = "아이디를 입력해주세요")
    private String LoginId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String LoginPw;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    private LocalDateTime regDate;
    private Boolean rank;
    public UserForm(){
    }
}