package com.example.demoshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoshop.entity.Customer;

public interface CustomerReposiory extends JpaRepository<Customer, Long> {

	Page<Customer> findByDeletedFalse(Pageable pageable);
}
