package com.lazish.service.implementations;

import com.lazish.dto.LessonDTO;
import com.lazish.entity.*;
import com.lazish.mapper.ExerciseMapper;
import com.lazish.mapper.LessonMapper;
import com.lazish.repository.LessonRepository;
import com.lazish.repository.TopicRepository;
import com.lazish.repository.UserLessonRepository;
import com.lazish.service.interfaces.LessonService;
import com.lazish.utils.key.UserLessonId;
import com.lazish.utils.key.UserTopicId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonServiceImpl implements LessonService {
    private final ExerciseServiceImpl exerciseService;
    private final LessonRepository lessonRepository;
    private final TopicRepository topicRepository;
    private final LessonMapper lessonMapper;
    private final TopicServiceImpl topicService;
    private final UserLessonRepository userLessonRepository;
    private final Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);

    @Override
    @Transactional
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
    @Transactional
    public LessonDTO updateLesson(UUID id, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lesson not found!"));
        logger.info("Updating lesson reward:{}", lessonDTO.getReward());
        if(lessonDTO.getReward() != 0) {
            lesson.setReward(lessonDTO.getReward());
        }
        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public LessonDTO addLessonToTopic(LessonDTO lessonDTO, UUID topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Topic not found!"));
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
        topic.setTotalLessons(topic.getTotalLessons() + 1);
        topicRepository.save(topic);
        return lessonMapper.toDto(lesson);
    }

    @Override
    @Transactional
    public void deleteLesson(UUID id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lesson not found!"));
        Topic topic = lessonRepository.getTopicByLessonId(id);
        topic.setTotalLessons(topic.getTotalLessons() - 1);
        lessonRepository.delete(lesson);
        topicRepository.save(topic);
    }

    @Override
    public LessonDTO getLesson(UUID id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lesson not found!"));
        return lessonMapper.toDto(lesson);
    }

    @Override
    @Transactional
    public void finishUserLesson(UUID userId, UUID lessonId) {
        if (userLessonRepository.existsById(new UserLessonId(userId, lessonId))) {
            if (userLessonRepository.getUserProgress(userId, lessonId) < 100) {
                UserLesson userLesson = userLessonRepository.findById(new UserLessonId(userId, lessonId)).orElseThrow(() -> new EntityNotFoundException("User lesson not found!"));
                userLesson.setProgress(100);
                userLessonRepository.save(userLesson);
                topicService.finishUserTopic(userId, lessonId);
            }
        }
        else{
            UserLesson userLesson = UserLesson
                    .builder()
                    .id(new UserLessonId(userId, lessonId))
                    .progress(100)
                    .build();
            userLessonRepository.save(userLesson);
            topicService.finishUserTopic(userId, lessonId);
        }
    }

    @Override
    public int getUserProgress(UUID userId, UUID lessonId) {
        return userLessonRepository.getUserProgress(userId, lessonId);
    }
}
