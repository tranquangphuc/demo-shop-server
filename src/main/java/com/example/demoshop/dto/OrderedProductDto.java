package com.example.demoshop.dto;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderedProductDto extends ProductDto {

	private OffsetDateTime orderedDate;
}
