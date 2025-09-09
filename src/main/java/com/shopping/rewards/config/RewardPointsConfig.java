package com.shopping.rewards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration class for reward points calculation thresholds.
 * Loads threshold values from application properties.
 */
@Component
public class RewardPointsConfig {
	
	@Value("${app.rewards-point1xThreshold:50}")
	private Integer rewardsPoints1XThreshold;
	
	@Value("${app.rewards-point2xThreshold:100}")
	private Integer rewardsPoints2XThreshold;
 
	/**
	 * Gets the threshold amount for 1x reward points calculation.
	 * @return the threshold amount above which 1x points are earned
	 */
	public Integer getRewardPoints1xThreshold() {
		return this.rewardsPoints1XThreshold;
	}
	
	/**
	 * Gets the threshold amount for 2x reward points calculation.
	 * @return the threshold amount above which 2x points are earned
	 */
	public Integer getRewardPoints2xThreshold() {
		return this.rewardsPoints2XThreshold;
	}

}