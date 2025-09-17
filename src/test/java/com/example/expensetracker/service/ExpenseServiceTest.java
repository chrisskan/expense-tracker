package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense coffeeExpense;
    private Expense rentExpense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        coffeeExpense = Expense.builder()
                .id("1")
                .amount(3.5)
                .business("CoffeeShop")
                .category("Food")
                .dateTime(LocalDateTime.now().minusDays(1))
                .build();

        rentExpense = Expense.builder()
                .id("2")
                .amount(500.0)
                .business("Landlord")
                .category("Housing")
                .dateTime(LocalDateTime.now().minusDays(10))
                .build();
    }

    @Test
    void testSaveExpense() {
        when(expenseRepository.save(coffeeExpense)).thenReturn(coffeeExpense);

        Expense saved = expenseService.saveExpense(coffeeExpense);

        assertThat(saved).isEqualTo(coffeeExpense);
        verify(expenseRepository, times(1)).save(coffeeExpense);
    }

    @Test
    void testGetExpenseById_ShouldReturnExpense() {
        when(expenseRepository.findById("1")).thenReturn(Optional.of(coffeeExpense));

        Optional<Expense> result = expenseService.getExpenseById("1");

        assertThat(result).isPresent().contains(coffeeExpense);
    }

    @Test
    void testGetExpenseById_ShouldReturnEmpty_WhenNotFound() {
        when(expenseRepository.findById("999")).thenReturn(Optional.empty());

        Optional<Expense> result = expenseService.getExpenseById("999");

        assertThat(result).isEmpty();
    }

    @Test
    void testGetAllExpenses() {
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(coffeeExpense, rentExpense));

        List<Expense> result = expenseService.getAllExpenses();

        assertThat(result).hasSize(2).containsExactly(coffeeExpense, rentExpense);
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseRepository).deleteById("1");

        expenseService.deleteExpense("1");

        verify(expenseRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByBusiness() {
        when(expenseRepository.findByBusinessContainingIgnoreCase("coffee"))
                .thenReturn(List.of(coffeeExpense));

        List<Expense> result = expenseService.findByBusiness("coffee");

        assertThat(result).containsExactly(coffeeExpense);
    }

    @Test
    void testFindByDateRange() {
        LocalDateTime from = LocalDateTime.now().minusDays(15);
        LocalDateTime to = LocalDateTime.now();

        when(expenseRepository.findByDateTimeBetween(from, to))
                .thenReturn(List.of(coffeeExpense, rentExpense));

        List<Expense> result = expenseService.findByDateRange(from, to);

        assertThat(result).hasSize(2).contains(coffeeExpense, rentExpense);
    }

    @Test
    void testFindByAmountGreaterThanEqual() {
        when(expenseRepository.findByAmountGreaterThanEqual(100.0))
                .thenReturn(List.of(rentExpense));

        List<Expense> result = expenseService.findByAmountGreaterThanEqual(100.0);

        assertThat(result).containsExactly(rentExpense);
    }

    @Test
    void testFindByCategory() {
        when(expenseRepository.findByCategory("Food")).thenReturn(List.of(coffeeExpense));

        List<Expense> result = expenseService.findByCategory("Food");

        assertThat(result).containsExactly(coffeeExpense);
    }
}
