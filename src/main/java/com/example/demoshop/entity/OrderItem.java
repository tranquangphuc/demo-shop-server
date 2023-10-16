package com.example.demoshop.entity;

import java.time.OffsetDateTime;

import com.example.demoshop.entity.OrderItem.OrderItemId;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(OrderItemId.class)
public class OrderItem {
	@Id
	public Long customerId;
	@Id
	public Long productId;
	@Id
	private OffsetDateTime orderedDate;

	@MapsId("customerId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "customerId")
	private Customer customer;

	@MapsId("productId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "productId")
	private Product product;

	public OrderItem(Long customerId, Long productId, OffsetDateTime orderedDate) {
		this.customerId = customerId;
		this.productId = productId;
		this.orderedDate = orderedDate;
	}

	public OrderItem(OrderItemId id, OffsetDateTime orderedDate) {
		this.customerId = id.customerId;
		this.productId = id.productId;
		this.orderedDate = orderedDate;
	}

	@Data
	@NoArgsConstructor
	@Embeddable
	public static class OrderItemId {
		private Long customerId;
		private Long productId;
		private OffsetDateTime orderedDate;
	}

}
