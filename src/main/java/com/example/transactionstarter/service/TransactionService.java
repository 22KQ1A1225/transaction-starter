package com.example.transactionstarter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionStatus;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // 1. Create Transaction
    public Transaction createTransaction(Transaction transaction) {

        // Transaction ID validation
        if (transaction.getTransactionId() == null
                || transaction.getTransactionId().isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction ID is required");
        }

        // Duplicate Transaction ID validation
        if (transactionRepository.existsById(
                transaction.getTransactionId())) {
            throw new RuntimeException(
                    "Transaction ID already exists");
        }

        // Amount validation
        if (transaction.getAmount() == null
                || transaction.getAmount().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        // Customer ID validation
        if (transaction.getCustomerId() == null
                || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID is required");
        }

        // Currency validation
        if (transaction.getCurrency() == null
                || transaction.getCurrency().isBlank()) {
            throw new IllegalArgumentException(
                    "Currency is required");
        }

        // Transaction Type validation
        if (transaction.getTransactionType() == null) {
            throw new IllegalArgumentException(
                    "Transaction type is required");
        }

        // Initial status
        // If no status is provided, transaction starts as PENDING
        if (transaction.getStatus() == null) {
            transaction.setStatus(TransactionStatus.PENDING);
        }

        return transactionRepository.save(transaction);
    }


    // 2. Get ALL Transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    // 3. Get ONE Transaction
    public Transaction getTransaction(String transactionId) {

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction ID is required");
        }

        return transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transaction not found"));
    }


    // 4. Update Transaction Status
    public Transaction updateStatus(
            String transactionId,
            TransactionStatus newStatus) {

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction ID is required");
        }

        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Status is required");
        }

        Transaction transaction =
                getTransaction(transactionId);

        TransactionStatus currentStatus =
                transaction.getStatus();

        // Completed and failed transactions are final
        if (currentStatus == TransactionStatus.COMPLETED
                || currentStatus == TransactionStatus.FAILED) {

            throw new IllegalStateException(
                    "Completed or failed transaction cannot be changed");
        }

        transaction.setStatus(newStatus);

        return transactionRepository.save(transaction);
    }


    // 5. Get all transactions for a customer
    public List<Transaction> getCustomerTransactions(
            String customerId) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID is required");
        }

        return transactionRepository.findByCustomerId(customerId);
    }
}