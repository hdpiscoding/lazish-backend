package com.lazish.quiz.exercise.translate;

import com.lazish.common.utils.converter.ListStringToJsonConverter;
import com.lazish.quiz.exercise.core.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "translate_exercises")
@PrimaryKeyJoinColumn(name = "exercise_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslateExercise extends Exercise {
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
}

