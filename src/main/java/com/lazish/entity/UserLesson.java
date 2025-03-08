package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.key.UserLessonId;
import jakarta.persistence.*;
import lombok.*;


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
    @MapsId("lessonId")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "progress")
    private int progress = 0;
}
