package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.CustomerTransaction;
import com.example.demo.model.RewardPoints;
import com.example.demo.repository.CustomerTransactionRepository;
import com.example.demo.repository.RewardPointsRepository;

public class TransactionServiceTest 
{
	@Mock
    private CustomerTransactionRepository transactionRepository;

    @Mock
    private RewardPointsRepository rewardPointsRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTransaction_Success() {
        // Arrange
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setCustomerId(1L);
        transaction.setAmount(120.0);
        transaction.setDate(LocalDate.now());

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        CustomerTransaction savedTransaction = transactionService.addTransaction(transaction);

        // Assert
        assertNotNull(savedTransaction);
        verify(transactionRepository, times(1)).save(transaction);
        verify(rewardPointsRepository, times(1)).save(any(RewardPoints.class));
    }

    @Test
    void testGetTransaction_Success() {
        // Arrange
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setId(1L);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        // Act
        CustomerTransaction result = transactionService.getTransaction(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testEditTransaction_Success() {
        // Arrange
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setId(1L);
        transaction.setCustomerId(1L);
        transaction.setAmount(80.0);
        transaction.setDate(LocalDate.now());

        when(transactionRepository.existsById(1L)).thenReturn(true);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        CustomerTransaction result = transactionService.editTransaction(1L, transaction);

        // Assert
        assertNotNull(result);
        assertEquals(80.0, result.getAmount());
        verify(transactionRepository, times(1)).existsById(1L);
        verify(transactionRepository, times(1)).save(transaction);
        verify(rewardPointsRepository, times(1)).save(any(RewardPoints.class));
    }

    @Test
    void testEditTransaction_NotFound() {
        // Arrange
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setId(1L);

        when(transactionRepository.existsById(1L)).thenReturn(false);

        // Act
        CustomerTransaction result = transactionService.editTransaction(1L, transaction);

        // Assert
        assertNull(result);
        verify(transactionRepository, times(1)).existsById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testDeleteTransaction() {
        // Act
        transactionService.deleteTransaction(1L);

        // Assert
        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetRewardPoints() {
        // Arrange
        RewardPoints reward1 = new RewardPoints();
        reward1.setCustomerId(1L);
        reward1.setPoints(50);

        RewardPoints reward2 = new RewardPoints();
        reward2.setCustomerId(1L);
        reward2.setPoints(100);

        when(rewardPointsRepository.findAll()).thenReturn(List.of(reward1, reward2));

        // Act
        int totalPoints = transactionService.getRewardPoints(1L);

        // Assert
        assertEquals(150, totalPoints);
        verify(rewardPointsRepository, times(1)).findAll();
    }

    @Test
    void testGetMonthlyRewardPoints() {
        // Arrange
        RewardPoints reward1 = new RewardPoints();
        reward1.setCustomerId(1L);
        reward1.setPoints(50);
        reward1.setMonth(1);

        RewardPoints reward2 = new RewardPoints();
        reward2.setCustomerId(1L);
        reward2.setPoints(100);
        reward2.setMonth(2);

        when(rewardPointsRepository.findAll()).thenReturn(List.of(reward1, reward2));

        // Act
        Map<java.time.Month, Integer> result = transactionService.getMonthlyRewardPoints(1L);

        // Assert
        assertNotNull(result);
        assertEquals(50, result.get(java.time.Month.JANUARY));
        assertEquals(100, result.get(java.time.Month.FEBRUARY));
        verify(rewardPointsRepository, times(1)).findAll();
    }

    @Test
    void testCalculateRewardPoints_Over100()
    {
        // Arrange
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setId(1L);
        transaction.setCustomerId(101L);
        transaction.setAmount(120.0);
        transaction.setDate(LocalDate.now());

        // Act
        int points = transactionService.calculateRewardPoints(transaction);

        // Assert
        assertEquals(40, points); // 40 points for 100-120 and 50 points for 50-100
    }
}
