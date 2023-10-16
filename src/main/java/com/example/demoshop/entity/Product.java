package com.example.demoshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String imageUrl;
	private Double price;
	private boolean deleted;

	@ManyToOne(optional = false)
	private Shop shop;

	public Product(String name, String imageUrl, Double price, Shop shop) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.price = price;
		this.shop = shop;
	}

}
