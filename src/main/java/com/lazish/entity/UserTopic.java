package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.key.UserTopicId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "UserTopic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTopic extends BaseEntity {
    @EmbeddedId
    private UserTopicId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("topicId")
    @JoinColumn(name = "topic", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "lesson_completed")
    private int lesson_completed = 0;
}
