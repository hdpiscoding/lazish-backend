package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.dto.ExerciseDTO;
import com.lazish.service.interfaces.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExerciseController extends BaseController {
    private final ExerciseService exerciseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExercise(@PathVariable UUID id) {
        exerciseService.deleteExercise(id);
        return buildResponse(null, HttpStatus.OK, "Exercise deleted successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateExercise(@PathVariable UUID id, @RequestBody ExerciseDTO exerciseDTO) {
        return buildResponse(exerciseService.updateExercise(id, exerciseDTO), HttpStatus.OK, "Exercise updated successfully");
    }
}
