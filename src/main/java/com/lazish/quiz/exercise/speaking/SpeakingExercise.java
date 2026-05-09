package com.lazish.quiz.exercise.speaking;

import com.lazish.quiz.exercise.core.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "speaking_exercises")
@PrimaryKeyJoinColumn(name = "exercise_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingExercise extends Exercise {
    @Column(name = "question")
    private String question;

    @Column(name = "audio")
    private String audio;
}

