package com.lazish.service.implementations;

import com.lazish.dto.LessonDTO;
import com.lazish.entity.Exercise;
import com.lazish.entity.Lesson;
import com.lazish.entity.Topic;
import com.lazish.mapper.LessonMapper;
import com.lazish.repository.LessonRepository;
import com.lazish.service.interfaces.LessonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonServiceImpl implements LessonService {
    private final ExerciseServiceImpl exerciseService;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public Lesson createLesson(LessonDTO lessonDTO, Topic topic) {
        Lesson lesson = lessonRepository.save(Lesson
                .builder()
                .reward(lessonDTO.getReward())
                .topic(topic)
                .build());

        Set<Exercise> exercises = lessonDTO.getExercises()
                .stream()
                .map(dto -> exerciseService.createExercise(dto, lesson))
                .collect(Collectors.toSet());

        lesson.setExercises(exercises);
        return lesson;
    }

    @Override
    public LessonDTO updateLesson(LessonDTO lessonDTO) {
        return null;
    }

    @Override
    public void deleteLesson(UUID id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public LessonDTO getLesson(UUID id) {
        return null;
    }
}
