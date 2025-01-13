package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer API", description = "Operations related to customers")
public class CustomerController 
{
	@Autowired
	private CustomerService customerService;
	
	@Operation(summary = "Test API", description = "Simple test endpoint")
	@ApiResponse(responseCode = "200", description = "Successful responses")
	@GetMapping("/test")
	public String test() 
	{
		return "Hello";
	}
	
	
	
	@Operation(summary = "Register a new customer", description = "Registers a new customer with name, email, password, and zone")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "customer registered successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Input data")
	})
	@PostMapping("/register")
	public ResponseEntity<Customer> resgisterCustomer(@Valid @RequestBody Customer customer)
	{
		return ResponseEntity.ok(customerService.registerCustomer(customer));
	}
	
	
	
	@Operation(summary = "Customer Login", description = "Allows a customer to log in using email and password.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login successful"),
			@ApiResponse(responseCode = "401", description = "Invalid email or password")
	})
	@PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@Valid @RequestBody Customer customer) {
        Optional<Customer> loggedInCustomer = customerService.loginCustomer(customer.getEmail(), customer.getPassword());
        if (loggedInCustomer.isPresent()) {
            return ResponseEntity.ok("Customer logged in successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
	
	
	
	@Operation(summary = "Log off a customer", description = "Logs off the customer from the system")
	@ApiResponse(responseCode = "200", description = "Logoff successful")
	@PostMapping("/logoff")
    public ResponseEntity<String> logoffCustomer(@RequestBody Customer customer)
	{
        return ResponseEntity.ok("Customer logged off successfully");
    }
	
	
	
	@Operation(summary = "Get customer by zone", description = "Fetches all customers belonging to a specific zone.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Customers fetched successfully",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Zone not found")
	})
	@GetMapping("/zone/{zoneId}")
	public ResponseEntity<List<Customer>> getCustomersByZone(@PathVariable Long zoneId)
	{
		List<Customer> customers = customerService.getCustomerByZoneId(zoneId);
		return ResponseEntity.ok(customers);
	} 
	
	
}
