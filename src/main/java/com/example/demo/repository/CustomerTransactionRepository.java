package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CustomerTransaction;

public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long>
{
	
}
