package com.lazish.mapper;

import com.lazish.base.BaseMapper;
import com.lazish.dto.ReelDTO;
import com.lazish.entity.Reel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReelMapper implements BaseMapper<Reel, ReelDTO> {
    private final ModelMapper modelMapper;

    @Override
    public ReelDTO toDto(Reel entity) {
        return modelMapper.map(entity, ReelDTO.class);
    }

    @Override
    public Reel toEntity(ReelDTO dto) {
        return modelMapper.map(dto, Reel.class);
    }

    @Override
    public List<ReelDTO> toDtoList(List<Reel> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Reel> toEntityList(List<ReelDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Set<ReelDTO> toDtoSet(Set<Reel> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<Reel> toEntitySet(Set<ReelDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
