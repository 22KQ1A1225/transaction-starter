package com.example.transactionstarter;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transactionstarter.service.TransactionService;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionStatus;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 1. GET ALL TRANSACTIONS
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // 2. GET ONE TRANSACTION
    @GetMapping("/{transactionId}")
    public Transaction getTransaction(
            @PathVariable String transactionId) {

        return transactionService.getTransaction(transactionId);
    }

    // 3. CREATE TRANSACTION
    @PostMapping
    public Transaction createTransaction(
            @RequestBody Transaction transaction) {

        return transactionService.createTransaction(transaction);
    }

    // 4. UPDATE TRANSACTION STATUS
    @PutMapping("/{transactionId}/status")
    public Transaction updateStatus(
            @PathVariable String transactionId,
            @RequestParam TransactionStatus newstatus) {

        return transactionService.updateStatus(
                transactionId,
                newstatus
        );
    }

    // 5. GET CUSTOMER TRANSACTIONS
    @GetMapping("/customer/{customerId}")
    public List<Transaction> getCustomerTransactions(
            @PathVariable String customerId) {

        return transactionService.getCustomerTransactions(customerId);
    }

    // HANDLE BAD REQUEST
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    // HANDLE INVALID STATUS CHANGE
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(
            IllegalStateException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}