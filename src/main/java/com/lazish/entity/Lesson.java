package com.lazish.entity;

import com.lazish.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Lesson")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Topic topic;

    @Column(name = "reward", nullable = false)
    private int reward;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserLesson> userLessons = new HashSet<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Exercise> exercises = new HashSet<>();
}
