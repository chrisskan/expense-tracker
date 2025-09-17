package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ---------- CRUD ----------
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense saved = expenseService.saveExpense(expense);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable String id) {
        return expenseService.getExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Queries ----------
    @GetMapping("/search/business")
    public ResponseEntity<List<Expense>> findByBusiness(@RequestParam String fragment) {
        return ResponseEntity.ok(expenseService.findByBusiness(fragment));
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<Expense>> findByDateRange(@RequestParam LocalDateTime from,
                                                         @RequestParam LocalDateTime to) {
        return ResponseEntity.ok(expenseService.findByDateRange(from, to));
    }

    @GetMapping("/search/amount")
    public ResponseEntity<List<Expense>> findByAmountGreaterThanEqual(@RequestParam Double minAmount) {
        return ResponseEntity.ok(expenseService.findByAmountGreaterThanEqual(minAmount));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<Expense>> findByCategory(@RequestParam String category) {
        return ResponseEntity.ok(expenseService.findByCategory(category));
    }
}
