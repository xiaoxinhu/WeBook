package com.example.demo.mapper;


import com.example.demo.model.order.Expense;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpenseMapper {
    int addExpense(Expense expense);
}
