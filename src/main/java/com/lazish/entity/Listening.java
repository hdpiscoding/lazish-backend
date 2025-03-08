package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.utils.converter.ListStringToJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Listening")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Listening extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson")
    private Lesson lesson;

    @Column(name = "audio", nullable = false)
    private String audio;

    @Convert(converter = ListStringToJsonConverter.class)
    @Column(name = "word_pool", nullable = false)
    private List<String> word_pool;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "placeholders", nullable = false)
    private int placeholders;
}
