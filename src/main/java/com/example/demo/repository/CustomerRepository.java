package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{

	Optional<Customer> findByEmail(String email);
	
	List<Customer> findAllByZoneId(Long zoneId);
	
}
