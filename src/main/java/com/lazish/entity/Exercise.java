package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.utils.converter.ListMapToJsonConverter;
import com.lazish.utils.converter.ListStringToJsonConverter;
import com.lazish.utils.enums.ExerciseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "Exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;

    @Column(name = "question")
    private String question;

    @Column(name = "audio")
    private String audio;

    @Convert(converter = ListStringToJsonConverter.class)
    @Column(name = "word_pool")
    private List<String> wordPool;

    @Column(name = "answer")
    private String answer;

    @Column(name = "placeholders")
    private int placeholders;

    @Convert(converter = ListMapToJsonConverter.class)
    @Column(name = "question_pair")
    private List<Map<String, String>> questionPair;

    @Convert(converter = ListMapToJsonConverter.class)
    @Column(name = "answer_pair")
    private List<Map<String, String>> answerPair;
}
