package com.example.transactionstarter.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByCustomerId(String customerId);
}