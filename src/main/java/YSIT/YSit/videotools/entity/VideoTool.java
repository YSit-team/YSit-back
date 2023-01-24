package YSIT.YSit.videotools.entity;

import YSIT.YSit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class VideoTool {
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    @Id
    @Column(name = "videoTool_id")
    private String id;
    @Column(unique = true)
    private String name;
    private int quantity;
    private int maxQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private String writer;
    @Builder
    public VideoTool(String id, String name, int quantity, int maxQuantity, User user, LocalDateTime regDate) {
        if (regDate == null) {
            regDate = LocalDateTime.now();
        }
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.user = user;
        this.writer = user.getId();
        this.regDate = regDate;

    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addMaxQuantity(int quantity) {
        this.maxQuantity += quantity;
    }

    public void removeQuantity(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalStateException("재고수량 초과");
        } else {
            this.quantity -= quantity;
        }
    }
}