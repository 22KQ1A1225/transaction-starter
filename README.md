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
- Eclipse

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

The database is created automatically when the Spring Boot application starts.

No separate database installation is required.

Transaction data is cleared when the application is restarted.

## H2 Console

After starting the Spring Boot application in Eclipse, the H2 Console can be opened in a web browser.

```text
http://localhost:8080/h2-console
```

### How to Open H2 Console

1. Start the Spring Boot application from Eclipse.
2. Open a web browser.
3. Enter:

```text
http://localhost:8080/h2-console
```

4. Enter the H2 database connection details configured in:

```text
src/main/resources/application.properties
```

5. Click **Connect**.
6. Open the transaction table.
7. Verify the transaction records stored in the database.

The H2 Console was used to verify that transactions created through Postman were successfully stored in the database.

> The exact JDBC URL, username, and password should be taken from the project's `application.properties` file.

# Testing

Automated tests were created using **JUnit 5 and Mockito**.

The project contains **8 automated tests** covering successful operations, validation, and business-rule scenarios.

## Test Cases

1. Successful transaction creation
2. Invalid transaction creation
3. Duplicate Transaction ID
4. Getting a transaction
5. Getting a non-existent transaction
6. Updating transaction status
7. Preventing changes to completed transactions
8. Preventing changes to failed transactions

## Automated Test Result

The tests were executed directly in Eclipse.

Steps used:

1. Right-click the `transaction-starter` project.
2. Select **Run As → JUnit Test**.
3. Check the JUnit result window.

Test result:

```text
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

A green bar in the Eclipse JUnit window indicates that all tests passed successfully.

# Postman Testing

The REST APIs were manually tested using Postman.

| Operation | Result |
|---|---|
| Create transaction | Passed |
| Get transaction | Passed |
| Update transaction status | Passed |
| Get customer transactions | Passed |
| Invalid amount | Passed - 400 Bad Request |

## API URLs Used in Postman

### Create Transaction

```text
POST http://localhost:8080/api/transactions
```

### Get Transaction

```text
GET http://localhost:8080/api/transactions/TXN007
```

### Update Transaction Status

```text
PUT http://localhost:8080/api/transactions/TXN007/status?newstatus=COMPLETED
```

### Get Customer Transactions

```text
GET http://localhost:8080/api/transactions/customer/CUST001
```

# H2 Database Verification

The H2 Console was used to verify that transaction data created through Postman was stored successfully in the database.

The complete flow was verified:

```text
Postman
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

# Screenshots

The `screenshots` folder contains evidence of the application testing:

- `01-post-success.png` — Successful transaction creation
- `02-get-success.png` — Successful transaction retrieval
- `03-put-success.png` — Successful status update
- `04-customer-transaction.png` — Customer transactions
- `05-bad-request.png` — Validation error
- `06-h2-database.png` — H2 database verification
- `07-Junit-tests.png` — JUnit test results

# Challenge Requirements Completed

The four required transaction operations from the engineering challenge have been implemented:

| Requirement | Status |
|---|---|
| Create Transaction | Completed |
| Get Transaction by ID | Completed |
| Update Transaction Status | Completed |
| Get Customer Transactions | Completed |
| Validation Rules | Completed |
| Duplicate Transaction ID Check | Completed |
| Automated Tests | Completed |
| H2 Database Verification | Completed |
| Postman API Testing | Completed |

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

# How to Run the Project

## Using Eclipse

The project is run directly from Eclipse.

1. Open Eclipse.
2. Open the `transaction-starter` project.
3. Make sure Java 17 is configured.
4. Open the main Spring Boot application class.
5. Right-click the main application class.
6. Select **Run As → Java Application**.
7. Wait for Spring Boot to start successfully.
8. Open Postman.
9. Use the API endpoints to test the application.

The application runs at:

```text
http://localhost:8080
```

# How to Run Tests

The automated tests are run directly from Eclipse using JUnit.

1. Open Eclipse.
2. Right-click the `transaction-starter` project.
3. Select **Run As → JUnit Test**.
4. Eclipse runs all automated tests.
5. Check the JUnit result window.

Expected result:

```text
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILDBUILD SUCCESS
```

# Known Limitations

- H2 is an in-memory database, so data is lost when the application restarts.
- Validation is implemented in the service layer.
- Error handling can be improved further using custom exception classes and a global exception handler.
- Authentication and authorization are outside the scope of this assignment.

# AI Assistance Disclosure

AI tools were used during development as permitted by the assignment.

ChatGPT was used to:

- Understand the assignment requirements.
- Understand Spring Boot concepts.
- Review the project structure.
- Review validation and business rules.
- Suggest and review test scenarios.
- Troubleshoot development issues.
- Improve README documentation.
- Understand H2 database verification.

AI-generated suggestions were reviewed and adapted to the actual project.

The final implementation was tested and verified using:

- Eclipse
- JUnit
- Postman
- H2 Console

Where AI suggestions did not match the actual project implementation or workflow, they were corrected before being used.

# Conclusion

This project implements the required transaction management operations using **Java, Spring Boot, Spring Data JPA, and H2**.

The application provides transaction creation, transaction retrieval, transaction status updates, and customer transaction retrieval.

Validation, business rules, automated testing, Postman API testing, and H2 database verification were completed.

The automated test suite contains **8 tests**, with all tests passing successfully in Eclipse.