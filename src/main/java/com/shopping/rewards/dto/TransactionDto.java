package com.shopping.rewards.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDto {
	private Integer id;
	private LocalDate date;
	private BigDecimal amount;
	private long points;
	private String customerId;

	public TransactionDto(Integer id,String customerId , LocalDate date, BigDecimal amount, long points) {
		this.id = id;
		this.customerId = customerId;
		this.date = date;
		this.amount = amount;
		this.points = points;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public long getPoints() {
		return points;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
	
}
