package com.lazish.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDTO {
    private UUID id;
    private String name;
    private String image;
    private int totalLessons;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<LessonDTO> lessons;
}
