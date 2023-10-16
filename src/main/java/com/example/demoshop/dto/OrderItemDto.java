package com.example.demoshop.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderItemDto {

	private Long customerId;
	private Long productId;
	private LocalDate orderedDate;
}
