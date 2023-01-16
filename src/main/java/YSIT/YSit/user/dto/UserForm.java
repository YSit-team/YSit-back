package YSIT.YSit.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserForm {
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    private String id;
    private String loginId;
    private String loginPw;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    private Boolean rank;
    @Builder
    public UserForm(String loginId, String loginPw, String name, LocalDateTime regDate, Boolean rank){
        if (regDate == null) regDate = LocalDateTime.now();
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.regDate = regDate;
        this.rank = rank;
    }
}