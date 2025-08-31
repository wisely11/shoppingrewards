package com.shopping.rewards.dto;

import java.util.List;

public class MonthlyReward {
	private String month; 
	private long points;
	private List<TransactionDto> transactions;
 	
	public MonthlyReward(String month, long points, List<TransactionDto> transactions) {
		this.month = month;
		this.points = points;
		this.transactions = transactions;
	}

	public String getMonth() {
		return month;
	}

	public long getPoints() {
		return points;
	}

	public List<TransactionDto> getTransactions() {
		return transactions;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public void setTransactions(List<TransactionDto> transactions) {
		this.transactions = transactions; 
	}
	
	
}
