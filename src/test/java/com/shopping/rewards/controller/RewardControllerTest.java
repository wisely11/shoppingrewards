package com.shopping.rewards.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.shopping.rewards.config.RewardPointsConfig;
import com.shopping.rewards.dto.RewardRequest;
import com.shopping.rewards.dto.RewardResponse;
import com.shopping.rewards.repository.CustomerRepository;
import com.shopping.rewards.repository.TransactionRepository;
import com.shopping.rewards.service.RewardService;

@WebMvcTest(RewardController.class)
@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean		
    private RewardService rewardService;
    
    @MockitoBean		 
	TransactionRepository txnRepo;

    @MockitoBean
	CustomerRepository customerRepo;

    @MockitoBean
	RewardPointsConfig rpConfig;


    @Test
    void testCalculateRewards_Success() throws Exception {
        RewardResponse response = new RewardResponse();
        response.setCustomerId("11111111-1111-1111-1111-111111111111");

        Mockito.when(rewardService.calculateRewards(Mockito.any(RewardRequest.class)))
               .thenReturn(response);

        String requestJson = "{"
                + "\"customerId\": \"11111111-1111-1111-1111-111111111111\","
                + "\"from\": \"2024-04-01\","
                + "\"to\": \"2025-07-30\""
                + "}";

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCalculateRewards_InvalidRequest() throws Exception {
        String invalidRequestJson = "{}";

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCalculateRewards_MissingCustomerId() throws Exception {
        String requestJson = "{"
                + "\"from\": \"2024-04-01\","
                + "\"to\": \"2025-07-30\""
                + "}";

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCalculateRewards_InvalidDateFormat() throws Exception {
        String requestJson = "{"
                + "\"customerId\": \"11111111-1111-1111-1111-111111111111\","
                + "\"from\": \"invalid-date\","
                + "\"to\": \"2025-07-30\""
                + "}";

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCalculateRewards_EmptyBody() throws Exception {
        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is4xxClientError());
    }
}
