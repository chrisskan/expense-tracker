package com.example.expensetracker.repository;

import com.example.expensetracker.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense coffeeExpense;
    private Expense groceriesExpense;
    private Expense rentExpense;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();

        coffeeExpense = Expense.builder()
                .amount(3.5)
                .business("CoffeeShop")
                .category("Food")
                .dateTime(LocalDateTime.now().minusDays(1))
                .build();

        groceriesExpense = Expense.builder()
                .amount(45.0)
                .business("Supermarket")
                .category("Food")
                .dateTime(LocalDateTime.now().minusDays(3))
                .build();

        rentExpense = Expense.builder()
                .amount(500.0)
                .business("Landlord")
                .category("Housing")
                .dateTime(LocalDateTime.now().minusDays(10))
                .build();

        expenseRepository.saveAll(List.of( coffeeExpense, groceriesExpense, rentExpense));
    }

    // ---------- findByCategory ----------
    @Test
    void testFindByCategory_ShouldReturnAllFoodExpenses() {
        List<Expense> result = expenseRepository.findByCategory("Food");
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Expense::getBusiness)
                .containsExactlyInAnyOrder("CoffeeShop", "Supermarket");
    }

    @Test
    void testFindByCategory_ShouldReturnSingleHousingExpense() {
        List<Expense> result = expenseRepository.findByCategory("Housing");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBusiness()).isEqualTo("Landlord");
    }

    @Test
    void testFindByCategory_ShouldReturnEmpty_WhenCategoryNotExists() {
        List<Expense> result = expenseRepository.findByCategory("Entertainment");
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByCategory_ShouldReturnEmpty_WhenCategoryIsEmpty() {
        List<Expense> result = expenseRepository.findByCategory("");
        assertThat(result).isEmpty();
    }


    // ---------- findByBusinessContainingIgnoreCase ----------
    @Test
    void testFindByBusinessContainingIgnoreCase_ShouldReturnMatchingResults() {
        List<Expense> result = expenseRepository.findByBusinessContainingIgnoreCase("coffee");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBusiness()).isEqualTo("CoffeeShop");
    }

    @Test
    void testFindByBusinessContainingIgnoreCase_ShouldReturnEmpty_WhenNoMatch() {
        List<Expense> result = expenseRepository.findByBusinessContainingIgnoreCase("gym");
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByBusinessContainingIgnoreCase_ShouldIgnoreCase() {
        List<Expense> result = expenseRepository.findByBusinessContainingIgnoreCase("SUPERMARKET");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBusiness()).isEqualTo("Supermarket");
    }


    // ---------- findByDateTimeBetween ----------
    @Test
    void testFindByDateTimeBetween_ShouldReturnExpensesInRange() {
        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();
        List<Expense> result = expenseRepository.findByDateTimeBetween(from, to);
        assertThat(result).hasSize(2); // coffee & groceries
    }

    @Test
    void testFindByDateTimeBetween_ShouldReturnEmpty_WhenNoExpensesInRange() {
        LocalDateTime from = LocalDateTime.now().minusDays(20);
        LocalDateTime to = LocalDateTime.now().minusDays(15);
        List<Expense> result = expenseRepository.findByDateTimeBetween(from, to);
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByDateTimeBetween_ShouldWorkWithSameFromAndTo() {
        LocalDateTime now = LocalDateTime.now();
        List<Expense> result = expenseRepository.findByDateTimeBetween(now, now);
        assertThat(result).isEmpty(); // δεν υπάρχει expense ακριβώς τη στιγμή "now"
    }

    // ---------- findByAmountGreaterThanEqual ----------
    @Test
    void testFindByAmountGreaterThanEqual_ShouldReturnMatchingExpenses() {
        List<Expense> result = expenseRepository.findByAmountGreaterThanEqual(50.0);
        assertThat(result).hasSize(1); // μόνο το rent 500
        assertThat(result.get(0).getBusiness()).isEqualTo("Landlord");
    }

    @Test
    void testFindByAmountGreaterThanEqual_ShouldReturnEmpty_WhenAllSmaller() {
        List<Expense> result = expenseRepository.findByAmountGreaterThanEqual(1000.0);
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByAmountGreaterThanEqual_ShouldIncludeEqualAmount() {
        List<Expense> result = expenseRepository.findByAmountGreaterThanEqual(45.0);
        assertThat(result).extracting(Expense::getBusiness)
                .contains("Supermarket", "Landlord"); // groceries 45.00 και rent 500
    }
}
