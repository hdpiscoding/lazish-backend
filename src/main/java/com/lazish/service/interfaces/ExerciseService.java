package com.lazish.service.interfaces;

import com.lazish.dto.ExerciseDTO;
import com.lazish.entity.Exercise;
import com.lazish.entity.Lesson;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
    Exercise createExercise(ExerciseDTO exerciseDTO, Lesson lesson);
    ExerciseDTO updateExercise(UUID id, ExerciseDTO exerciseDTO);
    void deleteExercise(UUID id);
    ExerciseDTO addExerciseToLesson(ExerciseDTO exerciseDTO, UUID lessonId);
}
