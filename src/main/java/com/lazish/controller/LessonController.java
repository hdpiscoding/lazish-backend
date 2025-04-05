package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.dto.ExerciseDTO;
import com.lazish.dto.LessonDTO;
import com.lazish.service.implementations.ExerciseServiceImpl;
import com.lazish.service.interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LessonController extends BaseController {
    private final LessonService lessonService;
    private final ExerciseServiceImpl exerciseService;

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
}
