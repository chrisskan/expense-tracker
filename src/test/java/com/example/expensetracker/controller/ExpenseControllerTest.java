package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense coffeeExpense;

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
    }

    @Test
    void testCreateExpense() {
        when(expenseService.saveExpense(coffeeExpense)).thenReturn(coffeeExpense);

        ResponseEntity<Expense> response = expenseController.createExpense(coffeeExpense);

        assertThat(response.getBody()).isEqualTo(coffeeExpense);
        verify(expenseService, times(1)).saveExpense(coffeeExpense);
    }

    @Test
    void testGetExpenseById_Found() {
        when(expenseService.getExpenseById("1")).thenReturn(Optional.of(coffeeExpense));

        ResponseEntity<Expense> response = expenseController.getExpenseById("1");

        assertThat(response.getBody()).isEqualTo(coffeeExpense);
    }

    @Test
    void testGetExpenseById_NotFound() {
        when(expenseService.getExpenseById("999")).thenReturn(Optional.empty());

        ResponseEntity<Expense> response = expenseController.getExpenseById("999");

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void testGetAllExpenses() {
        when(expenseService.getAllExpenses()).thenReturn(List.of(coffeeExpense));

        ResponseEntity<List<Expense>> response = expenseController.getAllExpenses();

        assertThat(response.getBody()).containsExactly(coffeeExpense);
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseService).deleteExpense("1");

        ResponseEntity<Void> response = expenseController.deleteExpense("1");

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(expenseService, times(1)).deleteExpense("1");
    }

    @Test
    void testFindByBusiness() {
        when(expenseService.findByBusiness("coffee")).thenReturn(List.of(coffeeExpense));

        ResponseEntity<List<Expense>> response = expenseController.findByBusiness("coffee");

        assertThat(response.getBody()).containsExactly(coffeeExpense);
    }

    @Test
    void testFindByAmountGreaterThanEqual() {
        when(expenseService.findByAmountGreaterThanEqual(3.0)).thenReturn(List.of(coffeeExpense));

        ResponseEntity<List<Expense>> response = expenseController.findByAmountGreaterThanEqual(3.00);

        assertThat(response.getBody()).containsExactly(coffeeExpense);
    }

    @Test
    void testFindByCategory() {
        when(expenseService.findByCategory("Food")).thenReturn(List.of(coffeeExpense));

        ResponseEntity<List<Expense>> response = expenseController.findByCategory("Food");

        assertThat(response.getBody()).containsExactly(coffeeExpense);
    }
}
