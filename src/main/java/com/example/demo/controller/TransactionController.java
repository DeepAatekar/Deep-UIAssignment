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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController 
{
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
    private TransactionService transactionService;
	
	@GetMapping("/add-transection")
	public String addTransection() 
	{
		return "Hello this is secured end point in transaction";
	}
	
	@PostMapping("/add")
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

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
    	CustomerTransaction getTranscation = transactionService.getTransaction(id);
    	Integer totalRewardPoints = transactionService.getRewardPoints(getTranscation.getCustomerId());
    	Map<Month, Integer> monthlyRewardPoints = transactionService.getMonthlyRewardPoints(getTranscation.getCustomerId());
    	TransactionResponse response = new TransactionResponse(getTranscation, totalRewardPoints,monthlyRewardPoints);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TransactionResponse> editTransaction(@PathVariable Long id, @RequestBody CustomerTransaction transaction) {
        CustomerTransaction updatedTranansaction = transactionService.editTransaction(id, transaction);
        Integer totalRewardPoints = transactionService.getRewardPoints(updatedTranansaction.getCustomerId());
    	Map<Month, Integer> monthlyRewardPoints = transactionService.getMonthlyRewardPoints(updatedTranansaction.getCustomerId());
    	TransactionResponse response = new TransactionResponse(updatedTranansaction, totalRewardPoints,monthlyRewardPoints);
        return ResponseEntity.ok(response);
        
    	
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    @GetMapping("/rewards/{customerId}")
    public ResponseEntity<Integer> getRewardPoints(@PathVariable Long customerId) {
        return ResponseEntity.ok(transactionService.getRewardPoints(customerId));
    }

    @GetMapping("/rewards")
    public ResponseEntity<List<RewardPoints>> getAllRewardPoints() {
        return ResponseEntity.ok(transactionService.getAllRewardPoints());
    }
}
