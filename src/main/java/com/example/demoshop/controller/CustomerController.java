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

import com.example.demoshop.dto.CustomerDetailsDto;
import com.example.demoshop.dto.CustomerDto;
import com.example.demoshop.dto.OrderItemDto;
import com.example.demoshop.dto.OrderedProductDto;
import com.example.demoshop.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("customers")
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping()
	public Page<CustomerDto> search(@PageableDefault(sort = { "email" }) Pageable pageable) {
		return service.search(pageable);
	}

	@PostMapping()
	public void create(@RequestBody CustomerDto customer) {
		log.debug("create customer: {}", customer);
		service.create(customer);
	}

	@GetMapping("{id}")
	public Optional<CustomerDetailsDto> select(@PathVariable Long id) {
		return service.select(id);
	}

	@PutMapping("{id}")
	public void update(@PathVariable Long id, @RequestBody CustomerDto customer) {
		log.debug("update customer: id={}, {}", id, customer);
		service.update(id, customer);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.debug("delete customer by id: {}", id);
		service.delete(id);
	}

	@GetMapping("{id}/orders")
	public Page<OrderedProductDto> searchOrderedProducts(@PathVariable Long id,
			@PageableDefault(sort = { "orderedDate" }, direction = Direction.DESC) Pageable pageable) {
		return service.searchOrderedProducts(id, pageable);
	}

	@PostMapping("{id}/orders")
	public void orderProduct(@PathVariable("id") Long customerId, @RequestBody OrderItemDto dto) {
		service.orderProduct(customerId, dto);
	}
}
