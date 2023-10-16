package com.example.demoshop.mapper;

import org.mapstruct.MappingTarget;

public interface DemoMapper<E, D> {

	E toEntity(D dto);

	void updateEntity(@MappingTarget E target, D source);

	D toDto(E entity);

	void updateDto(@MappingTarget D target, E source);

}
