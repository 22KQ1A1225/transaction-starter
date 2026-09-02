# Transaction Starter

A Spring Boot REST API for managing customer transactions.

This project was developed as part of the **Toucan Payments Engineering Challenge 2026**.

## Overview

The application implements four required operations:

1. Create a transaction
2. Get a transaction by Transaction ID
3. Update transaction status
4. Get all transactions for a Customer ID

## Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito
- Postman
- Git & GitHub

## Application Architecture

The project follows a simple layered architecture:

```text
Client / Postman
       |
       v
TransactionController
       |
       v
TransactionService
       |
       v
TransactionRepository
       |
       v
H2 Database
```

### Controller

`TransactionController` handles HTTP requests and provides the REST API endpoints.

### Service

`TransactionService` contains validation and transaction business logic.

### Repository

`TransactionRepository` uses Spring Data JPA to store and retrieve transactions.

### Entity

`Transaction` represents a transaction stored in the database.

## Transaction Model

| Field | Description |
|---|---|
| `transactionId` | Unique transaction identifier |
| `customerId` | Customer identifier |
| `amount` | Transaction amount |
| `currency` | Transaction currency |
| `transactionType` | Type of transaction |
| `status` | Current transaction status |

### Example Transaction

```json
{
  "transactionId": "TXN007",
  "customerId": "CUST001",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "status": "PENDING"
}
```

# Implemented Operations

## 1. Create Transaction

```text
POST /api/transactions
```

Creates and stores a new transaction.

Duplicate Transaction IDs are rejected.

### Example Request

```json
{
  "transactionId": "TXN007",
  "customerId": "CUST001",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "status": "PENDING"
}
```

## 2. Get Transaction

```text
GET /api/transactions/{transactionId}
```

Retrieves a transaction by Transaction ID.

### Example

```text
GET /api/transactions/TXN007
```

## 3. Update Transaction Status

```text
PUT /api/transactions/{transactionId}/status?newstatus=COMPLETED
```

Updates the status of an existing transaction.

### Example

```text
PUT /api/transactions/TXN007/status?newstatus=COMPLETED
```

## 4. Get Customer Transactions

```text
GET /api/transactions/customer/{customerId}
```

Returns all transactions belonging to the specified customer.

### Example

```text
GET /api/transactions/customer/CUST001
```

# Validation Rules

The following validation rules are implemented:

- Transaction ID is required and cannot be blank.
- Transaction ID must be unique.
- Customer ID is required and cannot be blank.
- Amount is required and must be greater than zero.
- Currency is required and cannot be blank.
- Transaction Type is required.
- Status is optional when creating a transaction.
- If status is not provided, it is automatically set to `PENDING`.

# Business and Status Rules

The following status rules are implemented:

- A transaction must exist before its status can be updated.
- A new status is required for a status update.
- `COMPLETED` transactions cannot be changed.
- `FAILED` transactions cannot be changed.
- Transactions that are not `COMPLETED` or `FAILED` can be updated.

### Reasoning

`COMPLETED` and `FAILED` are treated as final states to prevent changes to transactions that have already reached a terminal state.

# Error Handling

The application uses exceptions to handle invalid input and invalid status updates.

Examples include:

- Missing Transaction ID
- Missing Customer ID
- Missing Currency
- Missing Transaction Type
- Amount less than or equal to zero
- Missing status during a status update
- Attempting to change a `COMPLETED` transaction
- Attempting to change a `FAILED` transaction

### Example Error Response

```text
400 Bad Request

Amount must be greater than zero
```

# Database

The application uses **H2 with Spring Data JPA**.

H2 is configured as an in-memory database.

Transaction data is cleared when the application restarts.

### H2 Console

```text
http://localhost:8080/h2-console
```

The H2 Console was used to verify that transactions were stored successfully.

# Testing

Automated tests were created using **JUnit 5 and Mockito**.

The project contains **8 tests** covering successful operations, validation, and business-rule scenarios.

## Test Cases

1. Successful transaction creation
2. Invalid transaction creation
3. Duplicate Transaction ID
4. Getting a transaction
5. Getting a non-existent transaction
6. Updating transaction status
7. Preventing changes to completed transactions
8. Preventing changes to failed transactions

## Test Result

```text
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

ALL TESTS PASSED
```

# Postman Testing

The REST APIs were manually tested using Postman.

| Operation | Result |
|---|---|
| Create transaction | Passed |
| Get transaction | Passed |
| Update transaction status | Passed |
| Get customer transactions | Passed |
| Invalid amount | Passed - 400 Bad Request |

# H2 Database Verification

The H2 Console was used to verify that transactions created through the API were stored in the database.

The complete flow was verified:

```text
Postman
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
H2 Database
```

# Screenshots

The `screenshots` folder contains:

- `01-post-success.png` — Successful transaction creation
- `02-get-success.png` — Successful transaction retrieval
- `03-put-success.png` — Successful status update
- `04-customer-transaction.png` — Customer transactions
- `05-bad-request.png` — Validation error
- `06-h2-database.png` — H2 database verification
- `07-Junit-tests.png` — JUnit test results

# Project Structure

```text
transaction-starter/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.transactionstarter/
│   │   │       ├── TransactionController.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── TransactionService.java
│   │   │       │
│   │   │       └── transaction/
│   │   │           ├── Transaction.java
│   │   │           ├── TransactionRepository.java
│   │   │           ├── TransactionStatus.java
│   │   │           └── TransactionType.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── JUnit tests
│
├── screenshots/
│   ├── 01-post-success.png
│   ├── 02-get-success.png
│   ├── 03-put-success.png
│   ├── 04-customer-transaction.png
│   ├── 05-bad-request.png
│   ├── 06-h2-database.png
│   └── 07-Junit-tests.png
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
└── STUDENT_CHECKLIST.md
```

# How to Run

## Using Eclipse

1. Import the project as a Maven Project.
2. Ensure Java 17 is configured.
3. Open the main Spring Boot application class.
4. Select **Run As → Java Application**.
5. Wait for Spring Boot to start.
6. Use Postman to test the APIs.

The application runs on:

```text
http://localhost:8080
```

## Running Tests

The project includes the Maven Wrapper.

### Windows

```text
mvnw.cmd clean test
```

### Linux / macOS

```text
./mvnw clean test
```

# Known Limitations

- H2 is an in-memory database, so data is lost when the application restarts.
- Validation is implemented in the service layer.
- Error handling can be improved further using custom exception classes and a global exception handler.
- Authentication and authorization are outside the scope of this assignment.

# AI Usage Disclosure

ChatGPT was used during development to:

- Understand the assignment and Spring Boot concepts.
- Review code and validation rules.
- Suggest test scenarios.
- Troubleshoot development issues.
- Prepare project documentation.

The suggestions were reviewed and adapted to the actual project.

The final application was verified using Eclipse, Postman, H2 Console, and JUnit.

# Conclusion

This project implements the required transaction operations using Spring Boot, JPA, and H2.

It includes validation, business rules, exception handling, automated tests, and API/database verification.