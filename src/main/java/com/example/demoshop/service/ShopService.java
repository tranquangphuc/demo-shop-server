package com.example.demoshop.service;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demoshop.dto.ProductDto;
import com.example.demoshop.dto.ShopDto;
import com.example.demoshop.entity.Shop;
import com.example.demoshop.mapper.ProductMapper;
import com.example.demoshop.mapper.ShopMapper;
import com.example.demoshop.repository.ProductRepository;
import com.example.demoshop.repository.ShopRepository;

import jakarta.transaction.Transactional;

@Service
public class ShopService {

	private static final ShopMapper SHOP_MAPPER = Mappers.getMapper(ShopMapper.class);
	private static final ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

	private final ShopRepository shopRepository;
	private final ProductRepository productRepository;

	public ShopService(ShopRepository shopRepository, ProductRepository productRepository) {
		this.shopRepository = shopRepository;
		this.productRepository = productRepository;
	}

	public Page<ShopDto> search(Pageable pageable) {
		return shopRepository.findByDeletedFalse(pageable).map(SHOP_MAPPER::toDto);
	}

	@Transactional
	public void create(ShopDto dto) {
		Shop shop = SHOP_MAPPER.toEntity(dto);
		shopRepository.save(shop);
	}

	public Optional<ShopDto> select(Long id) {
		return shopRepository.findById(id).map(SHOP_MAPPER::toDto);
	}

	@Transactional
	public void update(Long id, ShopDto dto) {
		Shop entity = shopRepository.findById(id).orElseThrow();
		SHOP_MAPPER.updateEntity(entity, dto);
	}

	@Transactional
	public void delete(Long id) {
		shopRepository.findById(id).ifPresent(shop -> shop.setDeleted(true));
	}

	public Page<ProductDto> searchProducts(Long shopId, Pageable pageable) {
		return productRepository.findByShopId(shopId, pageable).map(PRODUCT_MAPPER::toDto);
	}
}
