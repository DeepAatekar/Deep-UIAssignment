package com.example.demo.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.CustomerController;
import com.example.demo.controller.TransactionController;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.model.RewardPoints;
import com.example.demo.service.CustomerService;
import com.example.demo.service.TransactionService;


public class ControllerTest
{
	@InjectMocks
	private CustomerController customerController;
	
	@InjectMocks
	private TransactionController transactionController;
	
	@Mock
	private CustomerService customerService;
	
	@Mock
	private TransactionService transactionService;
	
	@BeforeEach
	void setUp() 
	{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testResisterCustomer() 
	{
		Customer customer = new Customer();
		customer.setName("Deep Aatekar");
		customer.setEmail("deep@example.com");
		customer.setPassword("password1234");
		
		when(customerService.registerCustomer(customer)).thenReturn(customer);
		
		ResponseEntity<Customer> response = customerController.resgisterCustomer(customer);
		
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Deep Aatekar", response.getBody().getName());
	}
	
	
	
	@Test
	void testLoginCustomer_Success() 
	{
		Customer customer = new Customer();
		customer.setEmail("deep.aatekar@example.com");
		customer.setPassword("password123");
		
		when(customerService.loginCustomer("deep.aatekar@example.com", "password123"))
		.thenReturn(Optional.of(customer));
		
		ResponseEntity<String> response = customerController.loginCustomer(customer);
		
		assertEquals(200,response.getStatusCodeValue());
		assertEquals("Customer logged in successfully", response.getBody());
		
	}
	
	
	@Test
	void testLoginCustomer_Failure() 
	{
		Customer customer = new Customer();
		customer.setEmail("deep.aatekar@example.com");
		customer.setPassword("wrongpass");
		
		when(customerService.loginCustomer("deep.aatekar@example.com", "wrongpass"))
		.thenReturn(Optional.empty());
		
		ResponseEntity<String> response = customerController.loginCustomer(customer);
		
		assertEquals(401, response.getStatusCodeValue());
		assertEquals("Invalid email or password", response.getBody());
		
	}
	
//	@Test
//	void testAddTransaction() 
//	{
//		CustomerTransaction transaction = new CustomerTransaction();
//		transaction.setCustomerId(1L);
//		transaction.setAmount(100.0);
//		transaction.setDate(LocalDate.now());
//		
//		when(transactionService.addTransaction(transaction)).thenReturn(transaction);
//		
//		ResponseEntity<CustomerTransaction> response = transactionController.addTransaction(transaction);
//		
//		assertEquals(200, response.getStatusCodeValue());
//		assertEquals(100.0, response.getBody().getAmount());
//	}
/*	
	@Test
	void testGetTransaction() 
	{
		CustomerTransaction transaction = new CustomerTransaction();
		transaction.setCustomerId(1L);
		transaction.setAmount(100.0);
		transaction.setDate(LocalDate.now());
		
		when(transactionService.getTransaction(1L)).thenReturn(transaction);
		
		ResponseEntity<CustomerTransaction> response = transactionController.getTransaction(1L);
		
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(1L, response.getBody().getCustomerId());
	}
	*/
	
	@Test
	void testGetAllRewardPoints() 
	{
		RewardPoints reward1 = new RewardPoints();
		reward1.setCustomerId(1L);
		reward1.setPoints(100);
		RewardPoints reward2= new RewardPoints();
		reward2.setCustomerId(2L);
		reward2.setPoints(200);
		
		List<RewardPoints> rewards = Arrays.asList(reward1, reward2);
		
		when(transactionService.getAllRewardPoints()).thenReturn(rewards);
		
		ResponseEntity<List<RewardPoints>> respose = transactionController.getAllRewardPoints();
		
		assertEquals(200, respose.getStatusCodeValue());
		assertEquals(2, respose.getBody().size());
	}
}
