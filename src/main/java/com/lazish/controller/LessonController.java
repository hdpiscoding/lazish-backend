package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.dto.ExerciseDTO;
import com.lazish.dto.LessonDTO;
import com.lazish.service.implementations.ExerciseServiceImpl;
import com.lazish.service.implementations.JwtServiceImpl;
import com.lazish.service.interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LessonController extends BaseController {
    private final LessonService lessonService;
    private final ExerciseServiceImpl exerciseService;
    private final JwtServiceImpl jwtService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLesson(@PathVariable UUID id,@RequestBody LessonDTO lessonDTO) {
        return buildResponse(lessonService.updateLesson(id, lessonDTO), HttpStatus.OK, "Lesson updated successfully");
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getLessonDetails(@PathVariable UUID id) {
        return buildResponse(lessonService.getLesson(id), HttpStatus.OK, "Get lesson successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLesson(@PathVariable UUID id) {
        lessonService.deleteLesson(id);
        return buildResponse(null, HttpStatus.OK, "Lesson deleted successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/exercises")
    public ResponseEntity<Object> addExerciseToLesson(@PathVariable UUID id, @RequestBody ExerciseDTO exerciseDTO) {
        return buildResponse(exerciseService.addExerciseToLesson(exerciseDTO, id), HttpStatus.CREATED, "Exercise created successfully");
    }

    @GetMapping("/{id}/me/progress")
    public ResponseEntity<Object> getLessonProgress(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        Map<String, String> response = Map
                .of("lessonId", id.toString(), "userId", userId.toString(), "progress", String.valueOf(lessonService.getUserProgress(userId, id)));
        return buildResponse(response, HttpStatus.OK, "Get lesson progress successfully");
    }

    @PostMapping("/{id}/me/finish")
    public ResponseEntity<Object> finishLesson(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        lessonService.finishUserLesson(userId, id);
        return buildResponse(null, HttpStatus.OK, "Finish lesson successfully");
    }
}
