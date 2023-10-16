package com.example.demoshop.mapper;

import org.mapstruct.Mapper;

import com.example.demoshop.dto.CustomerDetailsDto;
import com.example.demoshop.dto.CustomerDto;
import com.example.demoshop.entity.Customer;

@Mapper(uses = { OrderItemMapper.class })
public abstract class CustomerMapper implements DemoMapper<Customer, CustomerDto> {

	public abstract CustomerDetailsDto toCustomerDetailsDto(Customer customer);

}
