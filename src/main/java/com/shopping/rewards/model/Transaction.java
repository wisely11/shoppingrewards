package com.shopping.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entity representing a transaction in the rewards system.
 */
@Entity
public class Transaction {
	@Id
	@GeneratedValue
	private Integer id;
	
    @NotNull
    @Column(nullable = false)
    private String customerId;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate date;
    
    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    public Transaction() {}
    
    public Transaction(String customerId, LocalDate date, BigDecimal amount) {
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
