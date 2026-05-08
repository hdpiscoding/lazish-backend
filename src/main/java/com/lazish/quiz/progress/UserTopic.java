package com.lazish.quiz.progress;

import com.lazish.common.base.BaseEntity;
import com.lazish.common.utils.key.UserTopicId;
import com.lazish.quiz.topic.Topic;
import com.lazish.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "topic_progress")
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "lesson_completed")
    private int lesson_completed = 0;
}
