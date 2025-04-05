package com.lazish.mapper;

import com.lazish.base.BaseMapper;
import com.lazish.dto.ExerciseDTO;
import com.lazish.entity.Exercise;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExerciseMapper implements BaseMapper<Exercise, ExerciseDTO> {
    private final ModelMapper modelMapper;


    @Override
    public ExerciseDTO toDto(Exercise entity) {
        return modelMapper.map(entity, ExerciseDTO.class);
    }

    @Override
    public Exercise toEntity(ExerciseDTO dto) {
        return modelMapper.map(dto, Exercise.class);
    }

    @Override
    public List<ExerciseDTO> toDtoList(List<Exercise> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Exercise> toEntityList(List<ExerciseDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Set<ExerciseDTO> toDtoSet(Set<Exercise> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<Exercise> toEntitySet(Set<ExerciseDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
