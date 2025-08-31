package com.shopping.rewards.dto;

import java.util.List;
import java.util.Map;

public class RewardResponse {
	private String customerId;
	private String customerName;
	private String from;
	private String to;
	private long totalPoints;
	private Map<Integer, List<MonthlyReward>> monthly; 

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
 
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public long getTotalPoints() {
		return totalPoints;
	}

	public Map<Integer, List<MonthlyReward>> getMonthly() {
		return monthly;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setTotalPoints(long totalPoints) {
		this.totalPoints = totalPoints;
	}

	public void setMonthly(Map<Integer, List<MonthlyReward>> monthlyPoints) {
		this.monthly = monthlyPoints;
	}
	
	
}
