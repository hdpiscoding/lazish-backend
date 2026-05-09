package com.lazish.quiz.exercise.translate;

import com.lazish.common.utils.enums.ExerciseType;
import com.lazish.quiz.exercise.core.Exercise;
import com.lazish.quiz.exercise.core.ExerciseDTO;
import com.lazish.quiz.exercise.core.strategy.ExerciseStrategy;
import com.lazish.quiz.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
public class TranslateExerciseStrategy implements ExerciseStrategy {
    @Override
    public ExerciseType getSupportedType() {
        return ExerciseType.TRANSLATE;
    }

    @Override
    public Exercise create(ExerciseDTO exerciseDTO, Lesson lesson) {
        TranslateExercise exercise = new TranslateExercise();
        exercise.setExerciseType(getSupportedType());
        exercise.setLesson(lesson);
        exercise.setQuestion(exerciseDTO.getQuestion());
        exercise.setAudio(exerciseDTO.getAudio());
        exercise.setWordPool(exerciseDTO.getWordPool());
        exercise.setAnswer(exerciseDTO.getAnswer());
        exercise.setPlaceholders(exerciseDTO.getPlaceholders());
        return exercise;
    }

    @Override
    public void update(Exercise exercise, ExerciseDTO exerciseDTO) {
        TranslateExercise translateExercise = cast(exercise);
        if (exerciseDTO.getQuestion() != null) { translateExercise.setQuestion(exerciseDTO.getQuestion()); }
        if (exerciseDTO.getAudio() != null) { translateExercise.setAudio(exerciseDTO.getAudio()); }
        if (exerciseDTO.getWordPool() != null) { translateExercise.setWordPool(exerciseDTO.getWordPool()); }
        if (exerciseDTO.getAnswer() != null) { translateExercise.setAnswer(exerciseDTO.getAnswer()); }
        if (exerciseDTO.getPlaceholders() != 0) { translateExercise.setPlaceholders(exerciseDTO.getPlaceholders()); }
    }

    private TranslateExercise cast(Exercise exercise) {
        if (!(exercise instanceof TranslateExercise)) {
            throw new IllegalStateException("Expected TranslateExercise but got: " + exercise.getClass().getSimpleName());
        }
        return (TranslateExercise) exercise;
    }
}

