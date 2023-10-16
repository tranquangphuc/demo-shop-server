package com.example.demoshop.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demoshop.entity.Product;

public class ProductSpecificationBuilder {

	private List<Specification<Product>> specs = new ArrayList<>();

	public ProductSpecificationBuilder nameLike(String name) {
		if (StringUtils.hasText(name)) {
			specs.add((root, query, builder) -> builder.like(builder.lower(root.get("name")),
					"%" + name.trim().toLowerCase() + "%"));
		}
		return this;
	}

	public ProductSpecificationBuilder priceLessThanOrEqualTo(Double maxPrice) {
		specs.add((root, query, builder) -> builder.lessThanOrEqualTo(root.get("price"), maxPrice));
		return this;
	}

	public ProductSpecificationBuilder priceGreaterThanOrEqualTo(Double minPrice) {
		specs.add((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("price"), minPrice));
		return this;
	}

	public ProductSpecificationBuilder priceInRange(Double minPrice, Double maxPrice) {
		specs.add((root, query, builder) -> builder.between(root.get("price"), minPrice, maxPrice));
		return this;
	}

	public ProductSpecificationBuilder priceInOptionalRange(Double minPrice, Double maxPrice) {
		if (minPrice != null && maxPrice != null) {
			priceInRange(minPrice, maxPrice);
		} else if (minPrice != null) {
			priceGreaterThanOrEqualTo(minPrice);
		} else if (maxPrice != null) {
			priceLessThanOrEqualTo(maxPrice);
		}
		return this;
	}

	public ProductSpecificationBuilder notDeleted() {
		specs.add((root, query, builder) -> builder.isFalse(root.get("deleted")));
		return this;
	}

	public ProductSpecificationBuilder shopIsNotDeleted() {
		specs.add((root, query, builder) -> builder.isFalse(root.get("shop").get("deleted")));
		return this;
	}

	public Specification<Product> build() {
		return Specification.allOf(specs);
	}
}
