package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerTransaction;
import com.example.demo.model.RewardPoints;
import com.example.demo.repository.CustomerTransactionRepository;
import com.example.demo.repository.RewardPointsRepository;

@Service
public class TransactionService
{
	    @Autowired
	    private CustomerTransactionRepository transactionRepository;

	    @Autowired
	    private RewardPointsRepository rewardPointsRepository;

	    public CustomerTransaction addTransaction(CustomerTransaction transaction) 
	    {
	        CustomerTransaction savedTransaction = transactionRepository.save(transaction);
	        calculateRewardPoints(savedTransaction);
	        return savedTransaction;
	    }

	    public CustomerTransaction getTransaction(Long id) {
	        return transactionRepository.findById(id).orElse(null);
	    }

	    public CustomerTransaction editTransaction(Long id, CustomerTransaction transaction) {
	        if (transactionRepository.existsById(id)) {
	            transaction.setId(id);
	            return transactionRepository.save(transaction);
	        }
	        return null;
	    }

	    public void deleteTransaction(Long id) {
	        transactionRepository.deleteById(id);
	    }

	    public int getRewardPoints(Long customerId) {
	        Optional<RewardPoints> rewardPointsList = rewardPointsRepository.findById(customerId);
	        return rewardPointsList.stream().mapToInt(RewardPoints::getPoints).sum();
	    }

	    public List<RewardPoints> getAllRewardPoints() {
	        return rewardPointsRepository.findAll();
	    }

	    private void calculateRewardPoints(CustomerTransaction transaction) {
	        double amount = transaction.getAmount();
	        int points = 0;

	        if (amount > 100) {
	            points += (amount - 100) * 2;
	            amount = 100;
	        }
	        if (amount > 50) {
	            points += (amount - 50);
	        }

	        RewardPoints rewardPoints = new RewardPoints();
	        rewardPoints.setCustomerId(transaction.getCustomerId());
	        rewardPoints.setMonth(transaction.getDate().getMonthValue());
	        rewardPoints.setYear(transaction.getDate().getYear());
	        rewardPoints.setPoints(points);

	        rewardPointsRepository.save(rewardPoints);
	    }
}
