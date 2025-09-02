package com.shopping.rewards.repository;

import java.time.LocalDate;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import com.shopping.rewards.model.Transaction;

/**
 * Repository interface for Transaction entity.
 * Provides CRUD operations and custom queries for Transaction.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> { 
    /**
     * Finds all transactions for a customer within a specified date range.
     *
     * @param customerId the unique customer identifier
     * @param from the start date (inclusive)
     * @param to the end date (inclusive)
     * @return a list of transactions matching the criteria
     */
    List<Transaction> findByCustomerIdAndDateBetween(String customerId, LocalDate from, LocalDate to); 
}

