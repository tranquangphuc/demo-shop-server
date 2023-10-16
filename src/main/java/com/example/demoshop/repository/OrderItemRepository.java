package com.example.demoshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoshop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItem.OrderItemId> {

	Page<OrderItem> findByCustomerId(Long customerId, Pageable pageable);
}
