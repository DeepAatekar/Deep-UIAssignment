package com.example.demo.dto;

import com.example.demo.model.CustomerTransaction;

public class TransactionResponse
{
	private CustomerTransaction customerTransaction;
	private Integer rewardpoints;
	
	public TransactionResponse(CustomerTransaction customerTransaction, Integer rewardpoints)
	{
		this.customerTransaction = customerTransaction;
		this.rewardpoints = rewardpoints;
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

	@Override
	public String toString() {
		return "TransactionResponse [customerTransaction=" + customerTransaction + ", rewardpoints=" + rewardpoints
				+ "]";
	}
	
	
	
	
	
}
