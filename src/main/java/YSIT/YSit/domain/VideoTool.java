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
public class VideoTool {
    @Id @GeneratedValue
    @Column(name = "videoTool_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private int quantity;
    private int maxQuantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admins admins;
    private LocalDateTime regDate;

    @Builder
    public VideoTool(Long id, String name, int quantity, int maxQuantity, Admins admin, LocalDateTime regDate) {
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.admins = admin;
        this.regDate = regDate;

    }

    public void removeQuantity(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalStateException("재고수량 초과");
        } else {
            this.quantity -= quantity;
        }
    }
}