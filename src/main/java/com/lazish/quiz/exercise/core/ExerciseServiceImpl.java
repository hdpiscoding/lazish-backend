package com.lazish.quiz.exercise.core;

import com.lazish.quiz.exercise.core.strategy.ExerciseStrategy;
import com.lazish.quiz.exercise.core.strategy.ExerciseStrategyFactory;
import com.lazish.quiz.lesson.Lesson;
import com.lazish.quiz.lesson.LessonRepository;
import com.lazish.common.utils.enums.ExerciseType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final LessonRepository lessonRepository;
    private final ApplicationContext applicationContext;
    private final ExerciseStrategyFactory exerciseStrategyFactory;


    @Override
    @Transactional
    public Exercise createExercise(ExerciseDTO exerciseDTO, Lesson lesson) {
        ExerciseType exerciseType = parseExerciseType(exerciseDTO.getExerciseType());
        ExerciseStrategy strategy = exerciseStrategyFactory.getStrategy(exerciseType);
        Exercise exercise = strategy.create(exerciseDTO, lesson);
        return exerciseRepository.save(exercise);
    }

    @Override
    @Transactional
    public ExerciseDTO updateExercise(UUID id, ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found!"));
        if (exerciseDTO.getExerciseType() != null) {
            ExerciseType requestedType = parseExerciseType(exerciseDTO.getExerciseType());
            if (requestedType != exercise.getExerciseType()) {
                throw new IllegalArgumentException("Exercise type cannot be changed once created");
            }
        }

        ExerciseStrategy strategy = exerciseStrategyFactory.getStrategy(exercise.getExerciseType());
        strategy.update(exercise, exerciseDTO);
        return exerciseMapper.toDto(exerciseRepository.save(exercise));
    }

    @Override
    @Transactional
    public void deleteExercise(UUID id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ExerciseDTO addExerciseToLesson(ExerciseDTO exerciseDTO, UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found!"));

        // Make sure @Transactional works correctly
        ExerciseServiceImpl self = applicationContext.getBean(ExerciseServiceImpl.class);
        Exercise exercise = self.createExercise(exerciseDTO, lesson);
        return exerciseMapper.toDto(exercise);
    }

    private ExerciseType parseExerciseType(String exerciseType) {
        if (exerciseType == null) {
            throw new IllegalArgumentException("Exercise type is required");
        }
        try {
            return ExerciseType.valueOf(exerciseType);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unsupported exercise type: " + exerciseType);
        }
    }
}
