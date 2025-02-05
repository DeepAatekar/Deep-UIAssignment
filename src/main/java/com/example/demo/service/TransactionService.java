package com.example.demo.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.CustomerTransaction;
import com.example.demo.model.RewardPoints;
import com.example.demo.repository.CustomerTransactionRepository;
import com.example.demo.repository.RewardPointsRepository;

@Service
public class TransactionService
{
	   private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    
	
	    @Autowired
	    private CustomerTransactionRepository transactionRepository;

	    @Autowired
	    private RewardPointsRepository rewardPointsRepository;

	    public CustomerTransaction addTransaction(CustomerTransaction transaction) 
	    {
	    	logger.info("addTransaction.transaction: {}", transaction);
	        
	    	CustomerTransaction savedTransaction = transactionRepository.save(transaction);
	        logger.info("addTransaction.savedTransaction: {}", savedTransaction);
	        
	        calculateRewardPoints(savedTransaction);
	        
	       
	        return savedTransaction;
	    }

	    public CustomerTransaction getTransaction(Long id) {
	        return transactionRepository.findById(id).orElse(null);
	    }

	    public CustomerTransaction editTransaction(Long id, CustomerTransaction transaction)
	    {
	        if (transactionRepository.existsById(id))
	        {
	            transaction.setId(id);
	            CustomerTransaction savedTransaction = transactionRepository.save(transaction);
	            calculateRewardPoints(savedTransaction);
	            return savedTransaction;
	        }
	        return null;
	    }

	    public void deleteTransaction(Long id) {
	        transactionRepository.deleteById(id);
	    }

	    
	    public int getRewardPoints(Long customerId) {
	    	 logger.info("CustomerId which get from method argument: {}", customerId);
	        //Optional<RewardPoints> rewardPointsList = rewardPointsRepository.findById(customerId);
	        List<RewardPoints> rewardPointsList = rewardPointsRepository.findAll()
	        		.stream()
	        		.filter(reward -> reward.getCustomerId().equals(customerId))
	        		.collect(Collectors.toList());
	    	 logger.info("getRewardPoints.rewardPointsList: {}", rewardPointsList);
	        
	    	 return rewardPointsList.stream().mapToInt(RewardPoints::getPoints).sum();
	    }

	    public List<RewardPoints> getAllRewardPoints() {
	        return rewardPointsRepository.findAll();
	    }

	    
	    
	  
	    
		public Map<Month, Integer> getMonthlyRewardPoints(Long customerId) 
		{
			logger.info("Fetching monthly reward points for customerId: {}", customerId);

			List<RewardPoints> rewardPointsList = rewardPointsRepository.findAll().stream()
					.filter(reward -> reward.getCustomerId().equals(customerId)).collect(Collectors.toList());

			return rewardPointsList.stream().collect(Collectors.groupingBy(reward -> Month.of(reward.getMonth()),
					Collectors.summingInt(RewardPoints::getPoints)));

		}
	    
	    
	       public int calculateRewardPoints(CustomerTransaction transaction)
	        {
				double amount = transaction.getAmount();
				int points = 0;

				logger.info("Calculating reward points for transaction: {}", transaction);

				if (amount > 100) 
				{
					points += (amount - 100) * 2;
					logger.debug("Points for amount over $100: {}", (amount - 100) * 2);
					//amount = 100;
				} 
				else if (amount > 50) 
				{
					points += (amount - 50) * 1;
					logger.debug("Points for amount between $50 and $100: {}", (amount - 50) * 1);
				}

//				RewardPoints rewardPoints = new RewardPoints();
//				rewardPoints.setCustomerId(transaction.getCustomerId());
//				rewardPoints.setMonth(transaction.getDate().getMonthValue());
//				rewardPoints.setYear(transaction.getDate().getYear());
//				rewardPoints.setPoints(points);
				
				RewardPoints rewardPoints = new RewardPoints().builder()
						.customerId(transaction.getCustomerId())
					    .month(transaction.getDate().getMonthValue())
					    .year(transaction.getDate().getYear())
					    .points(points)
					    .build();

				rewardPointsRepository.save(rewardPoints);

				logger.info("Reward points calculated: {}", points);
				logger.info("Reward points saved: {}", rewardPoints);

				return points;

	    } 
	    
	    
	    
	        
	    

	    
	    
	    
	    
}
