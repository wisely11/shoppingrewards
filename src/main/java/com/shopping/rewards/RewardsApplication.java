package com.shopping.rewards;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.shopping.rewards.model.Customer;
import com.shopping.rewards.model.Transaction;
import com.shopping.rewards.repository.CustomerRepository;
import com.shopping.rewards.repository.TransactionRepository;

/**
 * Main entry point for the Rewards Spring Boot application.
 */
@SpringBootApplication
public class RewardsApplication {

	private static final Logger logger = LoggerFactory.getLogger(RewardsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}

	/**
     * Seeds initial customer and transaction data into the database on application startup.
     * 
     * @param customers the customer repository
     * @param txRepo the transaction repository
     * @return a CommandLineRunner that performs the seeding
     */
	@Bean
	CommandLineRunner seedData(CustomerRepository customers, TransactionRepository txRepo) {
		logger.info("Seeding data for service");
		return args -> {
			Customer wisely = new Customer("11111111-1111-1111-1111-111111111111", "Wisely", "wisely@gmail.com", "");
			Customer samuel = new Customer("22222222-2222-2222-2222-222222222222", "Samuel", "samuel@gmail.com", "");
			customers.saveAll(Arrays.asList(wisely, samuel));
			txRepo.saveAll(Arrays.asList(
					new Transaction(wisely.getCustomerId(), LocalDate.of(2025, 5, 10), new BigDecimal("120.00")),
					new Transaction(wisely.getCustomerId(), LocalDate.of(2024, 5, 20), new BigDecimal("75.00")),
					new Transaction(wisely.getCustomerId(), LocalDate.of(2025, 6, 5), new BigDecimal("200.00")),
					new Transaction(wisely.getCustomerId(), LocalDate.of(2025, 7, 15), new BigDecimal("49.99")),
					new Transaction(samuel.getCustomerId(), LocalDate.of(2025, 5, 12), new BigDecimal("51.00")),
					new Transaction(samuel.getCustomerId(), LocalDate.of(2025, 6, 18), new BigDecimal("99.00")),
					new Transaction(samuel.getCustomerId(), LocalDate.of(2025, 7, 28), new BigDecimal("300.50"))));
			logger.info("Seeding completed!");
		};
	}

}
