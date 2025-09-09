package com.shopping.rewards.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.shopping.rewards.config.RewardPointsConfig;
import com.shopping.rewards.dto.MonthlyReward;
import com.shopping.rewards.dto.RewardRequest;
import com.shopping.rewards.dto.RewardResponse;
import com.shopping.rewards.exception.NotFoundException;
import com.shopping.rewards.mapper.TransactionMapper;
import com.shopping.rewards.model.Customer;
import com.shopping.rewards.model.Transaction;
import com.shopping.rewards.repository.CustomerRepository;
import com.shopping.rewards.repository.TransactionRepository;
import com.shopping.rewards.service.RewardService;

/**
 * Implementation of RewardService.
 * Handles the business logic for calculating customer reward points based on transactions.
 */
@Service
public class RewardServiceImpl implements RewardService {
	private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

	@Autowired
	private TransactionRepository txnRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private RewardPointsConfig rpConfig;

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
     * Calculates reward points for a customer based on their transactions within a date range.
     *
     * @param request the reward calculation request containing customer ID and date range
     * @return RewardResponse containing calculated points and details
     */
	@Override
	public RewardResponse calculateRewards(RewardRequest request) {
		String customerId = request.getCustomerId();

		// Validate if customer is present
		Optional<Customer> customer; 
		customer = customerRepo.findByCustomerId(customerId);  

		if (!customer.isPresent()) {
			logger.error("Customer with ID {} not found", customerId);
			throw new NotFoundException("Customer not found");
		}

		LocalDate fromDate;
		LocalDate toDate;
		fromDate = LocalDate.parse(request.getFrom(), FORMATTER);
		toDate = LocalDate.parse(request.getTo(), FORMATTER);

		List<Transaction> transactions;
		try {
			// Fetch transactions for the customer within the specified date range
            transactions = txnRepo.findByCustomerIdAndDateBetween(customerId, fromDate, toDate);
		} catch (DataAccessException e) {
			logger.error("Database error while fetching transactions for customer {}", customerId, e);
			throw new ServiceException("Database error occurred while fetching transactions");
		}

		// Map to hold monthly reward points grouped by year
        Map<Integer, List<MonthlyReward>> monthlyPoints = new ConcurrentHashMap<>();
		// Calculate total points using parallel stream for performance
        int totalPoints = transactions.parallelStream().mapToInt(t -> {
			int points = calculatePoints(t.getAmount());
			int year = t.getDate().getYear();
			String month = t.getDate().getMonth().toString();

			monthlyPoints.computeIfAbsent(year, y -> new CopyOnWriteArrayList<>());
			List<MonthlyReward> rewards = monthlyPoints.get(year);

			synchronized (rewards) {
				MonthlyReward existing = rewards.stream()
					.filter(r -> r.getMonth().equals(month))
					.findFirst().orElse(null);

				if (existing == null) {
					MonthlyReward newReward = new MonthlyReward(month, points,
						new ArrayList<>(List.of(TransactionMapper.toDto(t, points))));
					rewards.add(newReward);
				} else {
					existing.setPoints(existing.getPoints() + points);
					existing.getTransactions().add(TransactionMapper.toDto(t, points));
				}
			}
			return points;
		}).sum();

		RewardResponse result = new RewardResponse();
		result.setCustomerId(customerId);
		result.setCustomerName(customer.get().getName());
		result.setMonthly(monthlyPoints);
		result.setTotalPoints(totalPoints);
		result.setFrom(request.getFrom());
		result.setTo(request.getTo());
		return result;

	}

	/**
     * Calculates reward points for a given transaction amount based on configured thresholds.
     *
     * @param total the transaction amount
     * @return the calculated reward points
     */
	private int calculatePoints(BigDecimal total) {
		if (total == null) {
			logger.error("Transaction amount is null");
			throw new IllegalArgumentException("Transaction amount cannot be null");
		}

		double points = 0;
		double amount = total.doubleValue();

		if (amount > rpConfig.getRewardPoints2xThreshold()) {
			points += (amount - 100) * 2;
			points += 50;
		} else if (amount > rpConfig.getRewardPoints1xThreshold()) {
			points += (amount - 50);
		}
		return (int) Math.round(points);
	}
}
