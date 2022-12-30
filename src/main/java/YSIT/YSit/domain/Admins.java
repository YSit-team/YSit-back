package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Admins {
    @GeneratedValue @Id
    @Column(name = "admin_id")
    private Long id;
    @Column(unique = true)
    private String loginCode;
    private String name;
    private LocalDateTime regDate;

    @Builder
    public Admins (Long id, String loginCode, String name, LocalDateTime regDate) {
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }

        this.id = id;
        this.loginCode = loginCode;
        this.name = name;
        this.regDate = regDate;
    }
}
