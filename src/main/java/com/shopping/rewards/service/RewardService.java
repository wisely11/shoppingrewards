package com.shopping.rewards.service;
import com.shopping.rewards.dto.RewardRequest;
import com.shopping.rewards.dto.RewardResponse;


public interface RewardService {
    public RewardResponse calculateRewards(RewardRequest request);
}
