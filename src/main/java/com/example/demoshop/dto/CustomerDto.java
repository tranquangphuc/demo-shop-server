package com.example.demoshop.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CustomerDto {

	private Long id;
	private String name;
	private String email;
	private LocalDate birthday;
}
