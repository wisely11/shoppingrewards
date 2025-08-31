package com.shopping.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Transaction {
	@Id
	@GeneratedValue
	private Integer Id;
    private String customerId;
    private LocalDate date;
    private BigDecimal amount;

    public Transaction() {}
    
    public Transaction(String customerId, LocalDate date, BigDecimal amount) {
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
