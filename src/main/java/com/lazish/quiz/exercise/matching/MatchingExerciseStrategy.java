package com.lazish.quiz.exercise.matching;

import com.lazish.common.utils.enums.ExerciseType;
import com.lazish.quiz.exercise.core.Exercise;
import com.lazish.quiz.exercise.core.ExerciseDTO;
import com.lazish.quiz.exercise.core.strategy.ExerciseStrategy;
import com.lazish.quiz.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
public class MatchingExerciseStrategy implements ExerciseStrategy {
    @Override
    public ExerciseType getSupportedType() {
        return ExerciseType.MATCHING;
    }

    @Override
    public Exercise create(ExerciseDTO exerciseDTO, Lesson lesson) {
        MatchingExercise exercise = new MatchingExercise();
        exercise.setExerciseType(getSupportedType());
        exercise.setLesson(lesson);
        exercise.setQuestionPair(exerciseDTO.getQuestionPair());
        exercise.setAnswerPair(exerciseDTO.getAnswerPair());
        return exercise;
    }

    @Override
    public void update(Exercise exercise, ExerciseDTO exerciseDTO) {
        MatchingExercise matchingExercise = cast(exercise);
        if (exerciseDTO.getQuestionPair() != null) { matchingExercise.setQuestionPair(exerciseDTO.getQuestionPair()); }
        if (exerciseDTO.getAnswerPair() != null) { matchingExercise.setAnswerPair(exerciseDTO.getAnswerPair()); }
    }

    private MatchingExercise cast(Exercise exercise) {
        if (!(exercise instanceof MatchingExercise)) {
            throw new IllegalStateException("Expected MatchingExercise but got: " + exercise.getClass().getSimpleName());
        }
        return (MatchingExercise) exercise;
    }
}

