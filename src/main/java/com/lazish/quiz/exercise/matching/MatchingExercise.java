package com.lazish.quiz.exercise.matching;

import com.lazish.common.utils.converter.ListMapToJsonConverter;
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
import java.util.Map;

@Entity
@Table(name = "matching_exercises")
@PrimaryKeyJoinColumn(name = "exercise_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingExercise extends Exercise {
    @Convert(converter = ListMapToJsonConverter.class)
    @Column(name = "question_pair")
    private List<Map<String, String>> questionPair;

    @Convert(converter = ListMapToJsonConverter.class)
    @Column(name = "answer_pair")
    private List<Map<String, String>> answerPair;
}

