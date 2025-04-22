package com.lazish.service.implementations;

import com.lazish.dto.PaginatedResponseDTO;
import com.lazish.dto.TopicDTO;
import com.lazish.entity.Lesson;
import com.lazish.entity.Topic;
import com.lazish.entity.User;
import com.lazish.entity.UserTopic;
import com.lazish.mapper.TopicMapper;
import com.lazish.repository.LessonRepository;
import com.lazish.repository.TopicRepository;
import com.lazish.repository.UserRepository;
import com.lazish.repository.UserTopicRepository;
import com.lazish.service.interfaces.LessonService;
import com.lazish.service.interfaces.TopicService;
import com.lazish.utils.key.UserTopicId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;
    private final TopicMapper topicMapper;
    private final LessonService lessonService;
    private final UserTopicRepository userTopicRepository;
    private final UserRepository userRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper, @Lazy LessonService lessonService, LessonRepository lessonRepository, UserTopicRepository userTopicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
        this.userTopicRepository = userTopicRepository;
        this.userRepository = userRepository;
    }
    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Override
    public TopicDTO getTopicDetails(UUID id) {
        return topicMapper.toDto(topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found!")));
    }

    @Override
    public PaginatedResponseDTO<TopicDTO> getAllTopics(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<Topic> topics = topicRepository.findAll(pageRequest);
        List<TopicDTO> topicDTOs = topicMapper.toDtoList(topics.getContent()
                .stream()
                .peek(topic -> {
                    topic.setLessons(null);
                })
                .collect(Collectors.toList()));
        return new PaginatedResponseDTO<>(
                topicDTOs,
                topics.getNumber() + 1,
                topics.getSize(),
                topics.getTotalElements(),
                topics.getTotalPages(),
                topics.isLast()
        );
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
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found!"));
        Topic topic = lesson.getTopic();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        UserTopicId userTopicId = new UserTopicId(userId, topic.getId());
        if(userTopicRepository.existsById(userTopicId)) {
            UserTopic userTopic = userTopicRepository.findById(userTopicId).orElseThrow(() -> new EntityNotFoundException("User topic not found!"));
            if(userTopic.getLesson_completed() < topic.getTotalLessons()) {
                userTopic.setLesson_completed(userTopic.getLesson_completed() + 1);
                userTopicRepository.save(userTopic);
            }
            else{
                logger.info("User {} has already completed all lessons in topic {}", userId, topic.getId());
            }
        }
        else{
            UserTopic userTopic = UserTopic
                    .builder()
                    .id(userTopicId)
                    .user(user)
                    .topic(topic)
                    .lesson_completed(1)
                    .build();
            userTopicRepository.save(userTopic);
        }
    }
}
