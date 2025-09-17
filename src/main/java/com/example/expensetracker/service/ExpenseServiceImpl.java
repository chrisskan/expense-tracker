package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> getExpenseById(String id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> findByBusiness(String fragment) {
        return expenseRepository.findByBusinessContainingIgnoreCase(fragment);
    }

    @Override
    public List<Expense> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return expenseRepository.findByDateTimeBetween(from, to);
    }

    @Override
    public List<Expense> findByAmountGreaterThanEqual(Double minAmount) {
        return expenseRepository.findByAmountGreaterThanEqual(minAmount);
    }

    @Override
    public List<Expense> findByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }
}
