package com.lazish.quiz.exercise.listening;

import com.lazish.common.utils.enums.ExerciseType;
import com.lazish.quiz.exercise.core.Exercise;
import com.lazish.quiz.exercise.core.ExerciseDTO;
import com.lazish.quiz.exercise.core.strategy.ExerciseStrategy;
import com.lazish.quiz.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ListeningExerciseStrategy implements ExerciseStrategy {
    @Override
    public ExerciseType getSupportedType() {
        return ExerciseType.LISTENING;
    }

    @Override
    public Exercise create(ExerciseDTO exerciseDTO, Lesson lesson) {
        ListeningExercise exercise = new ListeningExercise();
        exercise.setExerciseType(getSupportedType());
        exercise.setLesson(lesson);
        exercise.setAudio(exerciseDTO.getAudio());
        exercise.setWordPool(exerciseDTO.getWordPool());
        exercise.setAnswer(exerciseDTO.getAnswer());
        exercise.setPlaceholders(exerciseDTO.getPlaceholders());
        return exercise;
    }

    @Override
    public void update(Exercise exercise, ExerciseDTO exerciseDTO) {
        ListeningExercise listeningExercise = cast(exercise);
        if (exerciseDTO.getAudio() != null) { listeningExercise.setAudio(exerciseDTO.getAudio()); }
        if (exerciseDTO.getWordPool() != null) { listeningExercise.setWordPool(exerciseDTO.getWordPool()); }
        if (exerciseDTO.getAnswer() != null) { listeningExercise.setAnswer(exerciseDTO.getAnswer()); }
        if (exerciseDTO.getPlaceholders() != 0) { listeningExercise.setPlaceholders(exerciseDTO.getPlaceholders()); }
    }

    private ListeningExercise cast(Exercise exercise) {
        if (!(exercise instanceof ListeningExercise)) {
            throw new IllegalStateException("Expected ListeningExercise but got: " + exercise.getClass().getSimpleName());
        }
        return (ListeningExercise) exercise;
    }
}

