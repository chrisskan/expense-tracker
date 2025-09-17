package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Expense saveExpense(Expense expense);
    Optional<Expense> getExpenseById(String id);
    List<Expense> getAllExpenses();
    void deleteExpense(String id);

    List<Expense> findByBusiness(String fragment);
    List<Expense> findByDateRange(LocalDateTime from, LocalDateTime to);
    List<Expense> findByAmountGreaterThanEqual(Double minAmount);
    List<Expense> findByCategory(String category);
}
