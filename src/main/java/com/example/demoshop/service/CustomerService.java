package com.example.demoshop.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demoshop.dto.CustomerDetailsDto;
import com.example.demoshop.dto.CustomerDto;
import com.example.demoshop.dto.OrderItemDto;
import com.example.demoshop.dto.OrderedProductDto;
import com.example.demoshop.entity.Customer;
import com.example.demoshop.entity.OrderItem;
import com.example.demoshop.entity.Product;
import com.example.demoshop.mapper.CustomerMapper;
import com.example.demoshop.mapper.OrderItemMapper;
import com.example.demoshop.repository.CustomerReposiory;
import com.example.demoshop.repository.OrderItemRepository;
import com.example.demoshop.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	private static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);
	private static final OrderItemMapper ORDER_ITEM_MAPPER = Mappers.getMapper(OrderItemMapper.class);

	private final CustomerReposiory customerReposiory;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;

	public CustomerService(CustomerReposiory customerReposiory, OrderItemRepository orderItemRepository,
			ProductRepository productRepository) {
		this.customerReposiory = customerReposiory;
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
	}

	public Page<CustomerDto> search(Pageable pageable) {
		return customerReposiory.findByDeletedFalse(pageable).map(CUSTOMER_MAPPER::toDto);
	}

	@Transactional
	public void create(CustomerDto dto) {
		Customer customer = CUSTOMER_MAPPER.toEntity(dto);
		customerReposiory.save(customer);
	}

	public Optional<CustomerDetailsDto> select(Long id) {
		return customerReposiory.findById(id).map(CUSTOMER_MAPPER::toCustomerDetailsDto);
	}

	@Transactional
	public void update(Long id, CustomerDto dto) {
		Customer entity = customerReposiory.findById(id).orElseThrow();
		CUSTOMER_MAPPER.updateEntity(entity, dto);
	}

	@Transactional
	public void delete(Long id) {
		customerReposiory.findById(id).ifPresent(customer -> customer.setDeleted(true));
	}

	public void orderProduct(Long customerId, OrderItemDto dto) {
		Customer customer = customerReposiory.findById(customerId).orElseThrow();
		Product product = productRepository.findById(dto.getProductId()).orElseThrow();
		OrderItem orderItem = new OrderItem(customer.getId(), product.getId(), OffsetDateTime.now());
		orderItemRepository.save(orderItem);
	}

	public Page<OrderedProductDto> searchOrderedProducts(Long id, Pageable pageable) {
		return orderItemRepository.findByCustomerId(id, pageable).map(ORDER_ITEM_MAPPER::toOrderedProductDto);
	}
}
