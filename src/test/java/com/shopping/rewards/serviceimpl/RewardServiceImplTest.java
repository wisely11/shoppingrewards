package com.shopping.rewards.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; 

import com.shopping.rewards.config.RewardPointsConfig;
import com.shopping.rewards.dto.RewardRequest;
import com.shopping.rewards.dto.RewardResponse;
import com.shopping.rewards.exception.NotFoundException;
import com.shopping.rewards.model.Customer;
import com.shopping.rewards.model.Transaction;
import com.shopping.rewards.repository.CustomerRepository;
import com.shopping.rewards.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

    @Mock
    private TransactionRepository txnRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private RewardPointsConfig rpConfig;

    @InjectMocks
    private RewardServiceImpl service;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId("INFO123");
        customer.setName("Wisely Samuel");

        lenient().when(rpConfig.getRewardPoints1xThreshold()).thenReturn(50);
        lenient().when(rpConfig.getRewardPoints2xThreshold()).thenReturn(100);
    }

    private RewardRequest buildRequest(String customerId,String from, String to) {
        RewardRequest request = new RewardRequest();
        request.setCustomerId(customerId);
        request.setFrom(from);
        request.setTo(to);
        return request;
    }

    @Test
    void testCustomerNotFound() {
        when(customerRepo.findByCustomerId("INFO124")).thenReturn(Optional.empty());

        RewardRequest request = buildRequest("INFO124", "2023-01-01", "2023-12-31");

        assertThrows(NotFoundException.class, () -> service.calculateRewards(request));
    }

    @Test
    void testNoTransactions() {
        when(customerRepo.findByCustomerId("INFO123")).thenReturn(Optional.of(customer));
        when(txnRepo.findByCustomerIdAndDateBetween(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        RewardRequest request = buildRequest("INFO123", "2023-01-01", "2023-12-31");

        RewardResponse response = service.calculateRewards(request);

        assertEquals("INFO123", response.getCustomerId());
        assertEquals("Wisely Samuel", response.getCustomerName());
        assertEquals(0, response.getTotalPoints());
        assertTrue(response.getMonthly().isEmpty());
    }

    @Test
    void testTransactionBelowThreshold_NoPoints() {
        when(customerRepo.findByCustomerId("INFO123")).thenReturn(Optional.of(customer));
        Transaction txn = new Transaction("INFO123",LocalDate.of(2023, 3, 15),BigDecimal.valueOf(40)); 

        when(txnRepo.findByCustomerIdAndDateBetween(any(), any(), any()))
                .thenReturn(List.of(txn));

        RewardRequest request = buildRequest("INFO123", "2023-01-01", "2023-12-31");
        RewardResponse response = service.calculateRewards(request);

        assertEquals(0, response.getTotalPoints());
        assertTrue(response.getMonthly().get(2023).get(0).getTransactions().get(0).getPoints() == 0);
    }

    @Test
    void testTransactionBetweenThresholds_1xPoints() {
        when(customerRepo.findByCustomerId("INFO123")).thenReturn(Optional.of(customer)); 
        Transaction txn = new Transaction("INFO123",LocalDate.of(2023, 4, 10),BigDecimal.valueOf(70)); 
        when(txnRepo.findByCustomerIdAndDateBetween(any(), any(), any()))
                .thenReturn(List.of(txn));

        RewardRequest request = buildRequest("INFO123", "2023-01-01", "2023-12-31");
        RewardResponse response = service.calculateRewards(request);

        assertEquals(20, response.getTotalPoints()); // 70-50 = 20
    }

    @Test
    void testTransactionAboveThreshold_2xPoints() {
        when(customerRepo.findByCustomerId("INFO123")).thenReturn(Optional.of(customer)); 
        Transaction txn = new Transaction("INFO123",LocalDate.of(2023, 5, 5),BigDecimal.valueOf(120));  
        when(txnRepo.findByCustomerIdAndDateBetween(any(), any(), any()))
                .thenReturn(List.of(txn));

        RewardRequest request = buildRequest("INFO123", "2023-01-01", "2023-12-31");
        RewardResponse response = service.calculateRewards(request);

        // (120-100)*2 + 50 = 90
        assertEquals(90, response.getTotalPoints());
    }

    @Test
    void testMultipleTransactionsSameMonth() {
        when(customerRepo.findByCustomerId("INFO123")).thenReturn(Optional.of(customer));
        Transaction txn1 = new Transaction("INFO123",LocalDate.of(2023, 6, 5),BigDecimal.valueOf(120));  
        Transaction txn2 = new Transaction("INFO123",LocalDate.of(2023, 6, 15),BigDecimal.valueOf(70)); 
        when(txnRepo.findByCustomerIdAndDateBetween(any(), any(), any()))
                .thenReturn(List.of(txn1, txn2));

        RewardRequest request = buildRequest("INFO123", "2023-01-01", "2023-12-31");
        RewardResponse response = service.calculateRewards(request);

        assertEquals(110, response.getTotalPoints()); // 90 + 20
        assertEquals(1, response.getMonthly().get(2023).size());
        assertEquals("JUNE", response.getMonthly().get(2023).get(0).getMonth());
    }

    @Test
    void testTransactionsAcrossYears() {
        when(customerRepo.findByCustomerId("INFO123")).thenReturn(Optional.of(customer));
        Transaction txn1 = new Transaction("INFO123",LocalDate.of(2023, 12, 31),BigDecimal.valueOf(120));  
        Transaction txn2 = new Transaction("INFO123",LocalDate.of(2024, 1, 1),BigDecimal.valueOf(70));   

        when(txnRepo.findByCustomerIdAndDateBetween(any(), any(), any()))
                .thenReturn(List.of(txn1, txn2));

        RewardRequest request = buildRequest("INFO123", "2023-01-01", "2024-12-31");
        RewardResponse response = service.calculateRewards(request);

        assertEquals(110, response.getTotalPoints());
        assertTrue(response.getMonthly().containsKey(2023));
        assertTrue(response.getMonthly().containsKey(2024));
    }

}
