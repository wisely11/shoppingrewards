package com.shopping.rewards.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.shopping.rewards.model.Customer;

/**
 * Repository interface for Customer entity.
 * Provides CRUD operations and custom queries for Customer.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { 	
    /**
     * Finds a customer by their customerId.
     * 
     * @param customerId the unique customer identifier
     * @return an Optional containing the Customer if found, or empty if not
     */
    Optional<Customer> findByCustomerId(String customerId);
}

