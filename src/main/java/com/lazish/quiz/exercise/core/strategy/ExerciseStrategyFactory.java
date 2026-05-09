package com.lazish.quiz.exercise.core.strategy;

import com.lazish.common.utils.enums.ExerciseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExerciseStrategyFactory {
    private final Map<ExerciseType, ExerciseStrategy> strategies;

    @Autowired
    public ExerciseStrategyFactory(List<ExerciseStrategy> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(
                ExerciseStrategy::getSupportedType,
                Function.identity(),
                (left, right) -> left,
                () -> new EnumMap<>(ExerciseType.class)
        ));
    }

    public ExerciseStrategy getStrategy(ExerciseType exerciseType) {
        ExerciseStrategy strategy = strategies.get(exerciseType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported exercise type: " + exerciseType);
        }
        return strategy;
    }
}

