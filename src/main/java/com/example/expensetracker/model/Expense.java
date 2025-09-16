package com.example.expensetracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "expenses")
public class Expense {

    @Id
    private String id;

    private BigDecimal amount;
    private String business;
    private LocalDateTime dateTime;
    private String category;
}
