package com.example.demo.controller;

import java.time.Month;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TransactionResponse;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.model.RewardPoints;
import com.example.demo.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transaction API", description = "Endpoints for managing customer transactions and rewards")
public class TransactionController 
{
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
    private TransactionService transactionService;
	
	@Operation(summary = "Test endpoint", description = "A simple secured test endpoint for transactions")
	@ApiResponse(responseCode = "200", description = "Test Successful")
	@GetMapping("/add-transection")
	public String addTransection() 
	{
		return "Hello this is secured end point in transaction";
	}
	
	
	@Operation(summary = "Add a new transaction", description = "Adds a new transaction record and calculate reward points")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Transaction added successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data")
	})
	@PostMapping("/add")
	@SecurityRequirement(name = "basicAuth")
    public ResponseEntity<TransactionResponse> addTransaction(@Valid @RequestBody CustomerTransaction transaction)
	{
		logger.info("Adding transaction: {}", transaction);
		CustomerTransaction savedTransaction = transactionService.addTransaction(transaction);
		logger.info("Saved Transaction: {}", savedTransaction);
		//Integer rewardPoints = transactionService.getRewardPoints(transaction.getCustomerId());
		Integer totalRewardPoints = transactionService.getRewardPoints(transaction.getCustomerId());
		logger.info("Total Reward Points: {}", totalRewardPoints);
		
		Map<Month, Integer> monthlyRewardPoints = transactionService.getMonthlyRewardPoints(transaction.getCustomerId());
		logger.info("Monthly Reward Points: {}",monthlyRewardPoints);
		
		//TransactionResponse response = new TransactionResponse(savedTransaction, rewardPoints);
		TransactionResponse response = new TransactionResponse(savedTransaction, totalRewardPoints,monthlyRewardPoints);
		logger.info("Transaction Response: {}", response);
		
		
        return ResponseEntity.ok(response);
    }
	
	

	@Operation(summary = "Get transaction by ID", description = "Fetches transaction by its ID and calculates reward points")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Trasaction fetched successfully"),
    		@ApiResponse(responseCode = "404", description = "Transaction not found")
    })
	@GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
    	CustomerTransaction getTranscation = transactionService.getTransaction(id);
    	Integer totalRewardPoints = transactionService.getRewardPoints(getTranscation.getCustomerId());
    	Map<Month, Integer> monthlyRewardPoints = transactionService.getMonthlyRewardPoints(getTranscation.getCustomerId());
    	TransactionResponse response = new TransactionResponse(getTranscation, totalRewardPoints,monthlyRewardPoints);
        return ResponseEntity.ok(response);
    }

	@Operation(summary = "Edit a transaction", description = "Allow admin to edit transaction")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
			@ApiResponse(responseCode = "403", description = "Forbidden for non admin user")
	})
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "basicAuth")
    public ResponseEntity<TransactionResponse> editTransaction(@PathVariable Long id, @RequestBody CustomerTransaction transaction) {
        CustomerTransaction updatedTranansaction = transactionService.editTransaction(id, transaction);
        Integer totalRewardPoints = transactionService.getRewardPoints(updatedTranansaction.getCustomerId());
    	Map<Month, Integer> monthlyRewardPoints = transactionService.getMonthlyRewardPoints(updatedTranansaction.getCustomerId());
    	TransactionResponse response = new TransactionResponse(updatedTranansaction, totalRewardPoints,monthlyRewardPoints);
        return ResponseEntity.ok(response);
        
    	
    }

	
	@Operation(summary = "Delete a transaction", description = "Allow admin to delete a transaction")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Transaction deleted successfully"),
			@ApiResponse(responseCode = "403", description = "Forbidden for non admin user")
	})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "basicAuth")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

	
	
	
	
	@Operation(summary = "Get reward point by customer ID", description = "Fetch total reward point for a specific customer.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reward points fetched successfully"),
			@ApiResponse(responseCode = "404", description = "Customer not found")
	})
    @GetMapping("/rewards/{customerId}")
    public ResponseEntity<Integer> getRewardPoints(@PathVariable Long customerId) {
        return ResponseEntity.ok(transactionService.getRewardPoints(customerId));
    }
	
	
	
	@Operation(summary = "Get all reward points", description = "Fetch reward points for all customers.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reward points fetched successfully")
	})
    @GetMapping("/rewards")
    public ResponseEntity<List<RewardPoints>> getAllRewardPoints() {
        return ResponseEntity.ok(transactionService.getAllRewardPoints());
    }
}
