package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.utils.converter.ListMapToJsonConverter;
import com.lazish.utils.converter.ListStringToJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Translate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translate extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;

    @Column(name = "question", nullable = false)
    private String question;

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
