package YSIT.YSit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Videotool {
    @Id @GeneratedValue
    @Column(name = "videotool_id")
    private Long id;

    private String vt_name;
    private int max_quantity;
}
