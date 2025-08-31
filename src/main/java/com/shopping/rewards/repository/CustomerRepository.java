package com.shopping.rewards.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.shopping.rewards.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { 	
    Optional<Customer> findByCustomerId(String customerId);
}

