package com.example.demoshop.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoshop.dto.ProductDto;
import com.example.demoshop.dto.ProductFormDto;
import com.example.demoshop.dto.ProductSearchFormDto;
import com.example.demoshop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@GetMapping()
	public Page<ProductDto> search(ProductSearchFormDto dto,
			@PageableDefault(sort = "price", direction = Direction.DESC) Pageable pageable) {
		log.debug("search product: form={}, pageable={}", dto, pageable);
		return service.search(dto, pageable);
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ProductDto create(ProductFormDto product) {
		log.debug("create product: {}", product);
		return service.create(product);
	}

	@GetMapping("{id}")
	public Optional<ProductDto> select(@PathVariable Long id) {
		return service.select(id);
	}

	@PostMapping(path = "{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void update(@PathVariable Long id, ProductFormDto product) {
		log.debug("update product: {}", product);
		service.update(id, product);
	}

	@PostMapping(path = "{id}/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void updateImage(@RequestPart MultipartFile imageFile) {
		log.debug("update product's image: {}", imageFile);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.debug("delete product by id: {}", id);
		service.delete(id);
	}
}
