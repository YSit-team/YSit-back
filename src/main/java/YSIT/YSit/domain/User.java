package YSIT.YSit.domain;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    public User (String name, String loginId, String loginPw, SchoolCategory schoolCategory, LocalDateTime regDate) {
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.schoolCategory = schoolCategory;
        this.regDate = regDate;
    }
}