package com.example.demoshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demoshop.dto.ProductDto;
import com.example.demoshop.entity.Product;

@Mapper
public abstract class ProductMapper implements DemoMapper<Product, ProductDto> {

	@Override
	@Mapping(target = "shop", ignore = true)
	public abstract void updateEntity(@MappingTarget Product entity, ProductDto dto);
}
