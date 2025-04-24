package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.dto.LessonDTO;
import com.lazish.dto.TopicDTO;
import com.lazish.service.implementations.LessonServiceImpl;
import com.lazish.service.implementations.TopicServiceImpl;
import com.lazish.service.interfaces.JwtService;
import com.lazish.service.interfaces.LessonService;
import com.lazish.service.interfaces.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TopicController extends BaseController {
    private final TopicService topicService;
    private final LessonService lessonService;
    private final JwtService jwtService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Object> createTopic(@RequestBody TopicDTO topic) {
        return buildResponse(topicService.createTopic(topic), HttpStatus.CREATED, "Create topic successfully");
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllTopics(@RequestParam int page,@RequestParam int limit) {
        return buildResponse(topicService.getAllTopics(page, limit), HttpStatus.OK, "Get all topics successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTopicDetails(@PathVariable UUID id) {
        return buildResponse(topicService.getTopicDetails(id), HttpStatus.OK, "Get topic successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTopic(@PathVariable UUID id, @RequestBody TopicDTO topic) {
        return buildResponse(topicService.updateTopic(id, topic), HttpStatus.OK, "Update topic successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTopic(@PathVariable UUID id) {
        topicService.deleteTopic(id);
        return buildResponse(null, HttpStatus.OK, "Delete topic successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/lessons")
    public ResponseEntity<Object> addLessonsToTopic(@PathVariable UUID id, @RequestBody LessonDTO lesson) {
        return buildResponse(lessonService.addLessonToTopic(lesson, id), HttpStatus.CREATED, "Add lessons to topic successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}/me")
    public ResponseEntity<Object> getUserProgress(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        Map<String, Object> userProgress = Map.of("userId", userId.toString(), "topicId", id.toString(), "progress", topicService.getUserProgress(userId, id));
        return buildResponse(userProgress, HttpStatus.OK, "Get user progress successfully");
    }
}
