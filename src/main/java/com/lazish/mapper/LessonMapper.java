package com.lazish.mapper;

import com.lazish.base.BaseMapper;
import com.lazish.dto.LessonDTO;
import com.lazish.entity.Lesson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LessonMapper implements BaseMapper<Lesson, LessonDTO> {
    private final ModelMapper modelMapper;

    @Override
    public LessonDTO toDto(Lesson entity) {
        return modelMapper.map(entity, LessonDTO.class);
    }

    @Override
    public Lesson toEntity(LessonDTO dto) {
        return modelMapper.map(dto, Lesson.class);
    }

    @Override
    public List<LessonDTO> toDtoList(List<Lesson> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Lesson> toEntityList(List<LessonDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Set<LessonDTO> toDtoSet(Set<Lesson> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<Lesson> toEntitySet(Set<LessonDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
