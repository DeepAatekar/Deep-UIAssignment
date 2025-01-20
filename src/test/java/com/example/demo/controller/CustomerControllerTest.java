package com.example.demo.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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
		mockCustomer.setEmail("deep.aatekae@example.com");
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
	
	
	@Test
	void test_loginCustomer_shouldReturnSuccess() 
	{
		// Arrange
		Customer mockCustomer = new Customer();
		mockCustomer.setEmail("deep.aatekar@example.com");
		mockCustomer.setPassword("password123");

		when(customerService.loginCustomer("deep.aatekar@example.com", "password123"))
				.thenReturn(Optional.of(mockCustomer));

		// Act
		ResponseEntity<String> response = customerController.loginCustomer(mockCustomer);

		// Assert
		assertThat(response.getBody()).isEqualTo("Customer logged in successfully");
		verify(customerService, times(1)).loginCustomer("deep.aatekar@example.com", "password123");
	}	
	
	
	@Test
	void test_loginCustomer_shouldReturnUnauthorized() 
	{
		// Arrange
		Customer mockCustomer = new Customer();
		mockCustomer.setEmail("deep.aatekar@example.com");
		mockCustomer.setPassword("password123");

		when(customerService.loginCustomer("deep.aatekar@example.com", "password123")).thenReturn(Optional.empty());

		// Act
		ResponseEntity<String> response = customerController.loginCustomer(mockCustomer);

		// Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(401);
		assertThat(response.getBody()).isEqualTo("Invalid email or password");
		verify(customerService, times(1)).loginCustomer("deep.aatekar@example.com", "password123");
	}

	    @Test
	    void test_logoffCustomer_shouldReturnSuccessMessage() {
	        // Arrange
	        Customer mockCustomer = new Customer();

	        // Act
	        ResponseEntity<String> response = customerController.logoffCustomer(mockCustomer);

	        // Assert
	        assertThat(response.getBody()).isEqualTo("Customer logged off successfully");
	    }

	    @Test
	    void test_getCustomersByZone_shouldReturnListOfCustomers() {
	        // Arrange
	        Customer mockCustomer1 = new Customer();
	        mockCustomer1.setId(1L);
	        mockCustomer1.setName("Deep Aatekar");

	        Customer mockCustomer2 = new Customer();
	        mockCustomer2.setId(2L);
	        mockCustomer2.setName("Yash Lekinwala");

	        when(customerService.getCustomerByZoneId(1L)).thenReturn(List.of(mockCustomer1, mockCustomer2));

	        // Act
	        ResponseEntity<List<Customer>> response = customerController.getCustomersByZone(1L);

	        // Assert
	        assertThat(response.getBody()).hasSize(2);
	        assertThat(response.getBody().get(0).getName()).isEqualTo("Deep Aatekar");
	        assertThat(response.getBody().get(1).getName()).isEqualTo("Yash Lekinwala");
	        verify(customerService, times(1)).getCustomerByZoneId(1L);
	    }
}
