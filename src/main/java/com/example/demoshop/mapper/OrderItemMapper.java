package com.example.demoshop.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demoshop.dto.OrderedProductDto;
import com.example.demoshop.entity.OrderItem;

@Mapper(uses = { ShopMapper.class })
public abstract class OrderItemMapper {

	public abstract List<OrderedProductDto> toProductDtoList(List<OrderItem> source);

	@Mapping(target = ".", source = "product")
	public abstract OrderedProductDto toOrderedProductDto(OrderItem source);
}
