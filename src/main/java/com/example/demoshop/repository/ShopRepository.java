package com.example.demoshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoshop.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {

	Page<Shop> findByDeletedFalse(Pageable pageable);
}
