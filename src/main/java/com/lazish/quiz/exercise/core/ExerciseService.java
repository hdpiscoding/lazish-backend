package com.lazish.quiz.exercise.core;

import com.lazish.quiz.lesson.Lesson;

import java.util.UUID;

public interface ExerciseService {
    Exercise createExercise(ExerciseDTO exerciseDTO, Lesson lesson);
    ExerciseDTO updateExercise(UUID id, ExerciseDTO exerciseDTO);
    void deleteExercise(UUID id);
    ExerciseDTO addExerciseToLesson(ExerciseDTO exerciseDTO, UUID lessonId);
}
