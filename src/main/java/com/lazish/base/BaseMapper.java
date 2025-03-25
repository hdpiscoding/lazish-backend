package com.lazish.base;

public interface BaseMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}
