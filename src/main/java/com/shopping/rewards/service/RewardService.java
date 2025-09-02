package com.shopping.rewards.service;
import com.shopping.rewards.dto.RewardRequest;
import com.shopping.rewards.dto.RewardResponse;

/**
 * Service interface for reward calculation logic.
 * Defines contract for calculating customer rewards based on transactions.
 */
public interface RewardService {
    /**
     * Calculates reward points for a customer based on the provided request.
     *
     * @param request the reward calculation request containing customer ID and date range
     * @return RewardResponse containing calculated points and details
     */
    public RewardResponse calculateRewards(RewardRequest request);
}
