package com.lazish.quiz.topic;

import com.lazish.common.dto.PaginatedResponseDTO;

import java.util.UUID;

public interface TopicService {
    TopicDTO getTopicDetails(UUID id);
    PaginatedResponseDTO<TopicDTO> getAllTopics(int page, int limit);
    TopicDTO createTopic(TopicDTO topicDTO);
    TopicDTO updateTopic(UUID id, TopicDTO topicDTO);
    void deleteTopic(UUID id);
    void finishUserTopic(UUID userId, UUID lessonId);
    int getUserProgress(UUID userId, UUID topicId);
}
