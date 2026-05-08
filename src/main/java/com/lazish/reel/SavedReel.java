package com.lazish.reel;

import com.lazish.common.base.BaseEntity;
import com.lazish.common.utils.key.UserReelId;
import com.lazish.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "saved_reel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedReel extends BaseEntity {
    @EmbeddedId
    private UserReelId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reel", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("reelId")
    private Reel reel;
}
