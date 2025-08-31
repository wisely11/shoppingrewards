package com.shopping.rewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/rewards")
public class RewardController { 
	
	@Autowired
	RewardService rewardService;
	
	@PostMapping
    public ResponseEntity<RewardResponse> calculateRewards(@Valid @RequestBody RewardRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.calculateRewards(request));
    }

}
