package YSIT.YSit.user.entity;

import YSIT.YSit.user.SchoolCategory;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    @Column(unique = true)
    private String loginId;
    @Column(length = 65)
    private String loginPw;
    @Enumerated(EnumType.STRING)
    private SchoolCategory schoolCategory;

    private LocalDateTime regDate;

    @Builder
    public User (Long id, String name, String loginId, String loginPw, SchoolCategory schoolCategory, LocalDateTime regDate) {
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