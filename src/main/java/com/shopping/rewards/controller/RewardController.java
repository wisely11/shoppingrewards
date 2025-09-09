package com.shopping.rewards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.rewards.dto.RewardRequest;
import com.shopping.rewards.dto.RewardResponse;
import com.shopping.rewards.service.RewardService;

import jakarta.validation.Valid;

/**
 * REST controller for handling reward calculation requests.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController { 
	
	private final RewardService rewardService;
	
	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}
	
    /**
     * Calculates reward points for a customer based on the provided request.
     * 
     * @param request the reward calculation request containing customer ID and date range
     * @return ResponseEntity containing the calculated reward points and customer details
     */
	@PostMapping
    public ResponseEntity<RewardResponse> calculateRewards(@Valid @RequestBody RewardRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.calculateRewards(request));
    }

}
