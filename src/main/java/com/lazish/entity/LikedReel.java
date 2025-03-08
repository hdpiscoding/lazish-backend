package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.key.UserReelId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LikedReel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedReel extends BaseEntity {
    @EmbeddedId
    private UserReelId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reel", nullable = false)
    @MapsId("reelId")
    private Reel reel;
}
