package com.example.demoshop.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductFormDto extends ProductDto {

	private MultipartFile imageFile;
}
