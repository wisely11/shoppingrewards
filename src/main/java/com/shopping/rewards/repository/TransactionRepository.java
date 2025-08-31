package com.shopping.rewards.repository;

import java.time.LocalDate;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import com.shopping.rewards.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> { 
    List<Transaction> findByCustomerIdAndDateBetween(String customerId, LocalDate from, LocalDate to); 
}

