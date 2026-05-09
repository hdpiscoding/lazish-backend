package com.lazish.quiz.exercise.speaking;

import com.lazish.common.utils.enums.ExerciseType;
import com.lazish.quiz.exercise.core.Exercise;
import com.lazish.quiz.exercise.core.ExerciseDTO;
import com.lazish.quiz.exercise.core.strategy.ExerciseStrategy;
import com.lazish.quiz.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SpeakingExerciseStrategy implements ExerciseStrategy {
    @Override
    public ExerciseType getSupportedType() {
        return ExerciseType.SPEAKING;
    }

    @Override
    public Exercise create(ExerciseDTO exerciseDTO, Lesson lesson) {
        SpeakingExercise exercise = new SpeakingExercise();
        exercise.setExerciseType(getSupportedType());
        exercise.setLesson(lesson);
        exercise.setQuestion(exerciseDTO.getQuestion());
        exercise.setAudio(exerciseDTO.getAudio());
        return exercise;
    }

    @Override
    public void update(Exercise exercise, ExerciseDTO exerciseDTO) {
        SpeakingExercise speakingExercise = cast(exercise);
        if (exerciseDTO.getQuestion() != null) { speakingExercise.setQuestion(exerciseDTO.getQuestion()); }
        if (exerciseDTO.getAudio() != null) { speakingExercise.setAudio(exerciseDTO.getAudio()); }
    }

    private SpeakingExercise cast(Exercise exercise) {
        if (!(exercise instanceof SpeakingExercise)) {
            throw new IllegalStateException("Expected SpeakingExercise but got: " + exercise.getClass().getSimpleName());
        }
        return (SpeakingExercise) exercise;
    }
}

