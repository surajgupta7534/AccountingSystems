package com.app.accountsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.accountsystem.entity.Expenses;

public interface ExpenseRepository extends MongoRepository<Expenses, String> {
}