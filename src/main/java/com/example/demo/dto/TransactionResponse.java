package com.example.demo.dto;

import java.time.Month;
import java.util.Map;

import com.example.demo.model.CustomerTransaction;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the response for a transaction including reward points and monthly breakdown")
public class TransactionResponse
{
	@Schema(description = "Details of the customer transaction")
	private CustomerTransaction customerTransaction;
	
	@Schema(description = "Total reward points earned by the customer",example = "150")
	private Integer rewardpoints;
	
	@Schema(description = "Breakdown of reward points by month", example = "{JANUARY=50}, FEBRUARY=100")
	private Map<Month, Integer> monthlyRewardPoints;
	
	
	//@Schema(description = "Constructor for creating a TransactionResponse object")
	public TransactionResponse(
			@Schema(description = "Details of the customer transaction")
			CustomerTransaction customerTransaction, 
			@Schema(description = "Total reward point earned by the customer", example = "150")
			Integer rewardpoints,
			@Schema(description = "Breakdown of reward points by month", example = "{JANUARY=50, FEBRUARY=100}")
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
