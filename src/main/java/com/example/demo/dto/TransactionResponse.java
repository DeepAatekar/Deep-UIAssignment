package com.example.demo.dto;

import java.time.Month;
import java.util.Map;

import com.example.demo.model.CustomerTransaction;

public class TransactionResponse
{
	private CustomerTransaction customerTransaction;
	private Integer rewardpoints;
	private Map<Month, Integer> monthlyRewardPoints;
	
	
	
	public TransactionResponse(CustomerTransaction customerTransaction, Integer rewardpoints,
			Map<Month, Integer> monthlyRewardPoints) {
		
		this.customerTransaction = customerTransaction;
		this.rewardpoints = rewardpoints;
		this.monthlyRewardPoints = monthlyRewardPoints;
	}
	public CustomerTransaction getCustomerTransaction() {
		return customerTransaction;
	}
	public void setCustomerTransaction(CustomerTransaction customerTransaction) {
		this.customerTransaction = customerTransaction;
	}
	public Integer getRewardpoints() {
		return rewardpoints;
	}
	public void setRewardpoints(Integer rewardpoints) {
		this.rewardpoints = rewardpoints;
	}
	public Map<Month, Integer> getMonthlyRewardPoints() {
		return monthlyRewardPoints;
	}
	public void setMonthlyRewardPoints(Map<Month, Integer> monthlyRewardPoints) {
		this.monthlyRewardPoints = monthlyRewardPoints;
	}
	
	@Override
	public String toString() {
		return "TransactionResponse [customerTransaction=" + customerTransaction + ", rewardpoints=" + rewardpoints
				+ ", monthlyRewardPoints=" + monthlyRewardPoints + "]";
	}
	
	

	
	
	
	
	
	
}
