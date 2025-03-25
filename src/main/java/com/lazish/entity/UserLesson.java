package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.utils.key.UserLessonId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "UserLesson")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLesson extends BaseEntity {
    @EmbeddedId
    private UserLessonId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("lessonId")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "progress")
    private int progress = 0;
}
