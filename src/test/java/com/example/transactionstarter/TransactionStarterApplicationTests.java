package com.example.transactionstarter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.transactionstarter.service.TransactionService;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionStatus;

@SpringBootTest
class TransactionStarterApplicationTests {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;


    @Test
    void createTransactionShouldRejectMissingTransactionId() {

        Transaction transaction = new Transaction();

        transaction.setCustomerId("CUST001");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals(
                "Transaction ID is required",
                exception.getMessage()
        );
    }


    @Test
    void createTransactionShouldRejectDuplicateTransactionId() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN001");

        when(transactionRepository.existsById("TXN001"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals(
                "Transaction ID already exists",
                exception.getMessage()
        );
    }


    @Test
    void createTransactionShouldRejectInvalidAmount() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN002");
        transaction.setAmount(BigDecimal.ZERO);

        when(transactionRepository.existsById("TXN002"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals(
                "Amount must be greater than zero",
                exception.getMessage()
        );
    }


    @Test
    void createTransactionShouldRejectMissingCustomerId() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN003");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");

        when(transactionRepository.existsById("TXN003"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals(
                "Customer ID is required",
                exception.getMessage()
        );
    }


    @Test
    void createTransactionShouldRejectMissingCurrency() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN004");
        transaction.setCustomerId("CUST001");
        transaction.setAmount(new BigDecimal("500.00"));

        when(transactionRepository.existsById("TXN004"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals(
                "Currency is required",
                exception.getMessage()
        );
    }


    @Test
    void getTransactionShouldReturnTransactionWhenItExists() {

        Transaction transaction = new Transaction(
                "TXN001",
                "CUST001",
                new BigDecimal("1000.50"),
                "INR",
                null,
                TransactionStatus.COMPLETED
        );

        when(transactionRepository.findById("TXN001"))
                .thenReturn(Optional.of(transaction));

        Transaction result =
                transactionService.getTransaction("TXN001");

        assertEquals(
                "TXN001",
                result.getTransactionId()
        );

        assertEquals(
                "CUST001",
                result.getCustomerId()
        );

        assertEquals(
                new BigDecimal("1000.50"),
                result.getAmount()
        );
    }


    @Test
    void completedTransactionShouldNotAllowStatusChange() {

        Transaction transaction = new Transaction(
                "TXN001",
                "CUST001",
                new BigDecimal("1000.50"),
                "INR",
                null,
                TransactionStatus.COMPLETED
        );

        when(transactionRepository.findById("TXN001"))
                .thenReturn(Optional.of(transaction));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transactionService.updateStatus(
                        "TXN001",
                        TransactionStatus.FAILED
                )
        );

        assertEquals(
                "Completed or failed transaction cannot be changed",
                exception.getMessage()
        );
    }


    @Test
    void failedTransactionShouldNotAllowStatusChange() {

        Transaction transaction = new Transaction(
                "TXN005",
                "CUST001",
                new BigDecimal("500.00"),
                "INR",
                null,
                TransactionStatus.FAILED
        );

        when(transactionRepository.findById("TXN005"))
                .thenReturn(Optional.of(transaction));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transactionService.updateStatus(
                        "TXN005",
                        TransactionStatus.COMPLETED
                )
        );

        assertEquals(
                "Completed or failed transaction cannot be changed",
                exception.getMessage()
        );
    }
}