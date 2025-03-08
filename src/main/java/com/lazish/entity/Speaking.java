package com.lazish.entity;

import com.lazish.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Speaking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Speaking extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson")
    private Lesson lesson;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "audio", nullable = false)
    private String audio;
}
