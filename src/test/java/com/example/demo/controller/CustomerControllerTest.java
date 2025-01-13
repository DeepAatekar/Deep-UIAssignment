package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

public class CustomerControllerTest 
{
	@InjectMocks
	private CustomerController  customerController;
	
	@Mock
	private CustomerService customerService;
	
	@BeforeEach
	void setUp() 
	{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void test_shouldReturnHello() 
	{
		
		//Act
		String response = customerController.test();
		
		//Assert
		assertThat(response).isEqualTo("Hello");
	}
	
	
	@Test
	void test_registerCustomer_shouldReturnSavedCustomer() 
	{
		//Arrange
		Customer mockCustomer = new Customer();
		mockCustomer.setId(1L);
		mockCustomer.setName("Deep Aatekar");
		mockCustomer.setEmail("deepaatekae@example.com");
		mockCustomer.setPassword("password123");
		
		when(customerService.registerCustomer(any(Customer.class))).thenReturn(mockCustomer);
		
		//Act
		ResponseEntity<Customer> response = customerController.resgisterCustomer(mockCustomer);
		
		//Assert
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1L);
		assertThat(response.getBody().getName()).isEqualTo("Deep Aatekar");
		verify(customerService, times(1)).registerCustomer(any(Customer.class));
	}
	
	
}
