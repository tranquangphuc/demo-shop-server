package com.example.demoshop.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoshop.dto.ProductDto;
import com.example.demoshop.dto.ShopDto;
import com.example.demoshop.service.ShopService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("shops")
public class ShopController {

	private final ShopService service;

	public ShopController(ShopService repository) {
		this.service = repository;
	}

	@GetMapping()
	public Page<ShopDto> search(@PageableDefault(sort = { "address" }, direction = Direction.DESC) Pageable pageable) {
		return service.search(pageable);
	}

	@PostMapping()
	public void create(@RequestBody ShopDto shop) {
		log.debug("create shop: {}", shop);
		service.create(shop);
	}

	@GetMapping("{id}")
	public Optional<ShopDto> select(@PathVariable Long id) {
		return service.select(id);
	}

	@PutMapping("{id}")
	public void update(@PathVariable Long id, @RequestBody ShopDto shop) {
		log.debug("update shop: {}", shop);
		service.update(id, shop);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.debug("delete shop by id: {}", id);
		service.delete(id);
	}

	@GetMapping("{id}/products")
	public Page<ProductDto> searchProduct(@PathVariable("id") Long shopId,
			@PageableDefault(sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
		return service.searchProducts(shopId, pageable);
	}
}
