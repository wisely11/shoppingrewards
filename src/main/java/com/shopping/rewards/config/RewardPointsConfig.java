package com.shopping.rewards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RewardPointsConfig {
	
	@Value("${app.rewards-point1xThreshold}")
	private String rewardsPoints1XThreshold;
	
	@Value("${app.rewards-point2xThreshold}")
	private String rewardsPoints2XThreshold;
 
	
	public Integer getRewardPoints1xThreshold() {
		return Integer.valueOf(this.rewardsPoints1XThreshold);
	}
	
	public Integer getRewardPoints2xThreshold() {
		return Integer.valueOf(this.rewardsPoints2XThreshold);
	}

} 