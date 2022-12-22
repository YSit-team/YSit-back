package YSIT.YSit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Board {
    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String category;
}
