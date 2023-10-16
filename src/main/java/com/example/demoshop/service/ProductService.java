package com.example.demoshop.service;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoshop.dto.ProductDto;
import com.example.demoshop.dto.ProductFormDto;
import com.example.demoshop.dto.ProductSearchFormDto;
import com.example.demoshop.entity.Product;
import com.example.demoshop.mapper.ProductMapper;
import com.example.demoshop.repository.ProductRepository;
import com.example.demoshop.repository.ShopRepository;
import com.example.demoshop.specification.ProductSpecificationBuilder;
import com.example.demoshop.storage.StorageService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	private static final ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

	private final ProductRepository productRepository;
	private final ShopRepository shopRepository;
	private final StorageService storageService;

	public ProductService(ProductRepository productRepository, ShopRepository shopRepository,
			StorageService storageService) {
		this.productRepository = productRepository;
		this.shopRepository = shopRepository;
		this.storageService = storageService;
	}

	public Page<ProductDto> search(ProductSearchFormDto dto, Pageable pageable) {
		Specification<Product> spec = new ProductSpecificationBuilder()
				.nameLike(dto.getName())
				.priceInOptionalRange(dto.getMinPrice(), dto.getMaxPrice())
				.shopIsNotDeleted()
				.notDeleted()
				.build();
		return productRepository.findAll(spec, pageable).map(PRODUCT_MAPPER::toDto);
	}

	@Transactional
	public ProductDto create(ProductFormDto dto) {
		Product product = PRODUCT_MAPPER.toEntity(dto);
		productRepository.save(product);
		updateProductImage(product, dto.getImageFile());
		return PRODUCT_MAPPER.toDto(product);
	}

	public Optional<ProductDto> select(Long id) {
		return productRepository.findById(id).map(PRODUCT_MAPPER::toDto);
	}

	@Transactional
	public void update(Long id, ProductFormDto dto) {
		Product entity = productRepository.findById(id).orElseThrow();
		PRODUCT_MAPPER.updateEntity(entity, dto);
		if (dto.getShop() != null && dto.getShop().getId() != null) {
			Long entityShopId = entity.getShop() != null ? entity.getShop().getId() : null;
			log.debug("change product's shop from {} to {}", entityShopId, dto.getShop().getId());
			shopRepository.findById(dto.getShop().getId()).ifPresent(shop -> entity.setShop(shop));
		}
		updateProductImage(entity, dto.getImageFile());
	}

	@Transactional
	public void delete(Long id) {
		productRepository.findById(id).ifPresent(product -> product.setDeleted(true));
	}

	private void updateProductImage(Product product, MultipartFile imageFile) {
		if (imageFile != null) {
			try {
				String imageUrl = storageService.store(imageFile, String.valueOf(product.getId()));
				product.setImageUrl(imageUrl);
			} catch (Exception e) {
				log.error("cannot store image of product {}", product.getId(), e);
			}
		}
	}

}
