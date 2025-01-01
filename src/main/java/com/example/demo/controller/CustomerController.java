package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController 
{
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/test")
	public String test() 
	{
		return "Hello";
	}
	
	@PostMapping("/register")
	public ResponseEntity<Customer> resgisterCustomer(@Valid @RequestBody Customer customer)
	{
		return ResponseEntity.ok(customerService.registerCustomer(customer));
	}
	
	@PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@Valid @RequestBody Customer customer) {
        Optional<Customer> loggedInCustomer = customerService.loginCustomer(customer.getEmail(), customer.getPassword());
        if (loggedInCustomer.isPresent()) {
            return ResponseEntity.ok("Customer logged in successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
	
	@PostMapping("/logoff")
    public ResponseEntity<String> logoffCustomer(@RequestBody Customer customer)
	{
        return ResponseEntity.ok("Customer logged off successfully");
    }
	
	
	
}
