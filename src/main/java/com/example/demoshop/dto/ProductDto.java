package com.example.demoshop.dto;

import lombok.Data;

@Data
public class ProductDto {

	private Long id;
	private String name;
	private String imageUrl;
	private Double price;
	private ShopDto shop;

}
