package YSIT.YSit.user.entity;

import YSIT.YSit.user.SchoolCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    @Id
    @Column(name = "user_id")
    private String id;
    private String name;
    @Column(unique = true)
    private String loginId;
    @Column(length = 65)
    private String loginPw;
    @Enumerated(EnumType.STRING)
    private SchoolCategory schoolCategory;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @Builder
    public User (String id, String name, String loginId, String loginPw, SchoolCategory schoolCategory, LocalDateTime regDate) {
        if (id == null || id.isEmpty()) id = String.valueOf(UUID.randomUUID());
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.schoolCategory = schoolCategory;
        this.regDate = regDate;
    }
    public void changeLoginId (String LoginId) {
        this.loginId = LoginId;
    }
    public void changeLoginPw (String LoginPw) {
        this.loginPw = LoginPw;
    }
    public void changeName (String name) {
        this.name = name;
    }
    public void changeSchoolCategory (SchoolCategory schoolCategory) {
        this.schoolCategory = schoolCategory;
    }
}