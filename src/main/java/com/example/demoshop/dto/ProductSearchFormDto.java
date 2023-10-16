package com.example.demoshop.dto;

import lombok.Data;

@Data
public class ProductSearchFormDto {

	private String name;
	private Double minPrice;
	private Double maxPrice;
}
