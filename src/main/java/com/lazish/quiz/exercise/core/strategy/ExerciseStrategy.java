package com.lazish.quiz.exercise.core.strategy;

import com.lazish.common.utils.enums.ExerciseType;
import com.lazish.quiz.exercise.core.ExerciseDTO;
import com.lazish.quiz.exercise.core.Exercise;
import com.lazish.quiz.lesson.Lesson;

public interface ExerciseStrategy {
    ExerciseType getSupportedType();
    Exercise create(ExerciseDTO exerciseDTO, Lesson lesson);
    void update(Exercise exercise, ExerciseDTO exerciseDTO);
}

