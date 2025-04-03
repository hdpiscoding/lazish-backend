package com.lazish.mapper;

import com.lazish.base.BaseMapper;
import com.lazish.dto.TopicDTO;
import com.lazish.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TopicMapper implements BaseMapper<Topic, TopicDTO> {
    private final ModelMapper modelMapper;

    @Override
    public TopicDTO toDto(Topic entity) {
        return modelMapper.map(entity, TopicDTO.class);
    }

    @Override
    public Topic toEntity(TopicDTO dto) {
        return modelMapper.map(dto, Topic.class);
    }

    @Override
    public List<TopicDTO> toDtoList(List<Topic> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Topic> toEntityList(List<TopicDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
