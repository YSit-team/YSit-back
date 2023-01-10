package YSIT.YSit.videotools.entity;

import YSIT.YSit.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String writer;
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public VideoTool(Long id, String name, int quantity, int maxQuantity, String writer, LocalDateTime regDate) {
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.writer = writer;
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