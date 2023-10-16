package com.example.demoshop.mapper;

import org.mapstruct.Mapper;

import com.example.demoshop.dto.ShopDto;
import com.example.demoshop.entity.Shop;

@Mapper
public abstract class ShopMapper implements DemoMapper<Shop, ShopDto> {

}
