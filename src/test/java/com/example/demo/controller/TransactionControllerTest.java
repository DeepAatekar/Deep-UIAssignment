package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.TransactionResponse;
import com.example.demo.model.CustomerTransaction;
import com.example.demo.model.RewardPoints;
import com.example.demo.service.TransactionService;

public class TransactionControllerTest
{
	@Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTransaction() {
        // Arrange
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setId(1L);
        transaction.setCustomerId(101L);
        transaction.setAmount(120.0);
        transaction.setDate(LocalDate.now());

        CustomerTransaction savedTransaction = transaction;
        int totalRewardPoints = 40;
        Map<Month, Integer> monthlyRewardPoints = Map.of(Month.JANUARY, 40);

        when(transactionService.addTransaction(transaction)).thenReturn(savedTransaction);
        when(transactionService.getRewardPoints(transaction.getCustomerId())).thenReturn(totalRewardPoints);
        when(transactionService.getMonthlyRewardPoints(transaction.getCustomerId())).thenReturn(monthlyRewardPoints);

        // Act
        ResponseEntity<TransactionResponse> response = transactionController.addTransaction(transaction);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedTransaction, response.getBody().getCustomerTransaction());
        assertEquals(totalRewardPoints, response.getBody().getRewardpoints());
        assertEquals(monthlyRewardPoints, response.getBody().getMonthlyRewardPoints());

        verify(transactionService, times(1)).addTransaction(transaction);
        verify(transactionService, times(1)).getRewardPoints(transaction.getCustomerId());
        verify(transactionService, times(1)).getMonthlyRewardPoints(transaction.getCustomerId());
    }

    @Test
    void testGetTransaction() {
        // Arrange
        Long transactionId = 1L;
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setId(transactionId);
        transaction.setCustomerId(101L);
        transaction.setAmount(120.0);
        transaction.setDate(LocalDate.now());

        int totalRewardPoints = 40;
        Map<Month, Integer> monthlyRewardPoints = Map.of(Month.JANUARY, 40);

        when(transactionService.getTransaction(transactionId)).thenReturn(transaction);
        when(transactionService.getRewardPoints(transaction.getCustomerId())).thenReturn(totalRewardPoints);
        when(transactionService.getMonthlyRewardPoints(transaction.getCustomerId())).thenReturn(monthlyRewardPoints);

        // Act
        ResponseEntity<TransactionResponse> response = transactionController.getTransaction(transactionId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transaction, response.getBody().getCustomerTransaction());
        assertEquals(totalRewardPoints, response.getBody().getRewardpoints());
        assertEquals(monthlyRewardPoints, response.getBody().getMonthlyRewardPoints());

        verify(transactionService, times(1)).getTransaction(transactionId);
        verify(transactionService, times(1)).getRewardPoints(transaction.getCustomerId());
        verify(transactionService, times(1)).getMonthlyRewardPoints(transaction.getCustomerId());
    }

    @Test
    void testDeleteTransaction() {
        // Arrange
        Long transactionId = 1L;
        doNothing().when(transactionService).deleteTransaction(transactionId);

        // Act
        ResponseEntity<String> response = transactionController.deleteTransaction(transactionId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Transaction deleted successfully", response.getBody());

        verify(transactionService, times(1)).deleteTransaction(transactionId);
    }

    @Test
    void testGetRewardPoints() {
        // Arrange
        Long customerId = 101L;
        int totalRewardPoints = 40;

        when(transactionService.getRewardPoints(customerId)).thenReturn(totalRewardPoints);

        // Act
        ResponseEntity<Integer> response = transactionController.getRewardPoints(customerId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(totalRewardPoints, response.getBody());

        verify(transactionService, times(1)).getRewardPoints(customerId);
    }

    @Test
    void testGetAllRewardPoints() {
        // Arrange
        List<RewardPoints> rewardPointsList = List.of(
                new RewardPoints(1L, 101L, 1, 2023, 50),
                new RewardPoints(2L, 102L, 2, 2023, 30)
        );

        when(transactionService.getAllRewardPoints()).thenReturn(rewardPointsList);

        // Act
        ResponseEntity<List<RewardPoints>> response = transactionController.getAllRewardPoints();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(rewardPointsList, response.getBody());

        verify(transactionService, times(1)).getAllRewardPoints();
    }
}
