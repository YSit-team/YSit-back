package YSIT.YSit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity(name = "admins")
@Getter
public class Admins {
    @GeneratedValue @Id
    @Column(name = "admin_id")
    private Long id;
    private String logincode;
    private String ad_name;

    private LocalDateTime regDate;
}
