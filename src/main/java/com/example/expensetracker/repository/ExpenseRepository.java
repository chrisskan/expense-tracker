package com.example.expensetracker.repository;

import com.example.expensetracker.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByCategory(String category);

    List<Expense> findByBusinessContainingIgnoreCase(String fragment);

    List<Expense> findByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    List<Expense> findByAmountGreaterThanEqual(Double minAmount);


}
