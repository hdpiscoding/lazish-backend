package com.lazish.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazish.dto.TopicDTO;
import com.lazish.entity.Lesson;
import com.lazish.entity.Topic;
import com.lazish.mapper.TopicMapper;
import com.lazish.repository.LessonRepository;
import com.lazish.repository.TopicRepository;
import com.lazish.repository.UserLessonRepository;
import com.lazish.service.interfaces.TopicService;
import com.lazish.utils.key.UserLessonId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;
    private final UserLessonRepository userLessonRepository;
    private final TopicMapper topicMapper;
    private final LessonServiceImpl lessonService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Override
    public TopicDTO getTopicDetails(UUID id) {
        return topicMapper.toDto(topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found!")));
    }

    @Override
    public List<TopicDTO> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topicMapper.toDtoList(topics
                .stream()
                .peek(topic -> {
                    topic.setLessons(null);
                })
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public TopicDTO createTopic(TopicDTO topicDTO) {
        Topic topic = topicRepository.save(Topic
                .builder()
                .name(topicDTO.getName())
                .image(topicDTO.getImage())
                .build());

        Set<Lesson> lessons = topicDTO.getLessons()
                .stream()
                .map(dto -> lessonService.createLesson(dto, topic))
                .collect(Collectors.toSet());

        topic.setLessons(lessons);
        topic.setTotalLessons(lessons.size());

        return topicMapper.toDto(topic);
    }

    @Override
    @Transactional
    public TopicDTO updateTopic(UUID id, TopicDTO topicDTO) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found!"));
        if(topicDTO.getName() != null) {
            topic.setName(topicDTO.getName());
        }
        if(topicDTO.getImage() != null) {
            topic.setImage(topicDTO.getImage());
        }
        return topicMapper.toDto(topicRepository.save(topic));
    }

    @Override
    @Transactional
    public void deleteTopic(UUID id) {
        topicRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void finishUserTopic(UUID userId, UUID lessonId) {
        Topic topic = lessonRepository.getTopicByLessonId(lessonId);
        topic.setTotalLessons(topic.getTotalLessons() + 1);
        topicRepository.save(topic);
    }
}
