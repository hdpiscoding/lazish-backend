package com.lazish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {
    private UUID id;
    private String question;
    private String answer;
    private String exerciseType;
    private String audio;
    private List<String> wordPool;
    private int placeholders;
    private List<Map<String, String>> questionPair;
    private List<Map<String, String>> answerPair;
}
