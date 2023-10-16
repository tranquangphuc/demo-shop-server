package com.example.demoshop.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;
	private boolean deleted;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
	private List<Product> products;

	public Shop(String name, String address) {
		this.name = name;
		this.address = address;
	}
}
