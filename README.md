# Transaction Starter

A Spring Boot REST API for managing customer transactions.

This project was developed as part of the *Toucan Payments Engineering Challenge 2026*.

## Overview

The application implements four required operations:
1.  Create a transaction
2.  Get a transaction by Transaction ID
3.  Update transaction status
4.  Get all transactions for a Customer ID

## Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database (In-memory)
- Maven Wrapper
- JUnit 5 & Mockito
- Postman
- Git & GitHub

## Application Architecture

The project follows a simple layered architecture:

Client / Postman
       |
       v
TransactionController  -> Handles HTTP requests
       |
       v
TransactionService     -> Validation & Business Logic
       |
       v
TransactionRepository  -> Spring Data JPA
       |
       v
H2 Database            -> Storage

## Transaction Model

### Fields
Field | Description
`transactionId` | Unique transaction identifier
`customerId` | Customer identifier
`amount` | Transaction amount
`currency` | Transaction currency (e.g., INR)
`transactionType` | Type of transaction (e.g., PAYMENT)
`status` | Current transaction status (e.g., PENDING)

### Example
{
  "transactionId": "TXN007",
  "customerId": "CUST001",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "status": "PENDING"
}

## Implemented Operations & API Endpoints

### 1. Create Transaction
*POST* `/api/transactions`
Creates and stores a new transaction. Duplicate Transaction IDs are rejected.

*Example Request:*
{
  "transactionId": "TXN007",
  "customerId": "CUST001",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "status": "PENDING"
}

### 2. Get Transaction by ID
*GET* `/api/transactions/{transactionId}`
*Example:* `GET /api/transactions/TXN007`

### 3. Update Transaction Status
*PUT* `/api/transactions/{transactionId}/status?newStatus=COMPLETED`
*Example:* `PUT /api/transactions/TXN007/status?newStatus=COMPLETED`

### 4. Get All Transactions for a Customer
*GET* `/api/transactions/customer/{customerId}`
*Example:* `GET /api/transactions/customer/CUST001`

## Validation Rules

- Transaction ID is required and cannot be blank.
- Transaction ID must be unique.
- Customer ID is required and cannot be blank.
- Amount is required and must be greater than zero.
- Currency is required and cannot be blank.
- Transaction Type is required.
- Status is optional when creating a transaction.
- If status is not provided, it is automatically set to `PENDING`.

## Business & Status Transition Rules

- A transaction must exist before its status can be updated.
- A new status is required for a status update.
- `COMPLETED` transactions cannot be changed. (Final State)
- `FAILED` transactions cannot be changed. (Final State)
- Transactions that are not `COMPLETED` or `FAILED` can be updated.

*Reasoning:* COMPLETED and FAILED are treated as final states to prevent changes to transactions that have already reached a terminal state.

## Error Handling

The application uses exceptions to handle invalid input.

*Examples:*
- Missing Transaction ID / Customer ID / Currency / Transaction Type
- Amount <= 0
- Missing status during update
- Attempting to change a COMPLETED or FAILED transaction

*Example Response:*
400 Bad Request
Amount must be greater than zero

## Database

- Uses H2 in-memory database with Spring Data JPA.
- Data is cleared when the application restarts.
- H2 Console is available at: `http://localhost:8080/h2-console`
- H2 Console was used to verify transactions were stored successfully.

## Testing

### Automated Tests (JUnit 5 + Mockito)

8 tests covering success and validation scenarios:
1. Successful transaction creation
2. Invalid transaction creation
3. Duplicate Transaction ID
4. Getting a transaction
5. Getting a non-existent transaction
6. Updating transaction status
7. Preventing changes to COMPLETED transactions
8. Preventing changes to FAILED transactions

*Test Result:*

Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
ALL TESTS PASSED

### Postman Testing

Operation | Result
Create transaction | Passed
Get transaction | Passed
Update transaction status | Passed
Get customer transactions | Passed
Invalid amount | Passed - 400 Bad Request

## Screenshots

The `screenshots` folder contains:
- `01-post-success.png` - Successful transaction creation
- `02-get-success.png` - Successful transaction retrieval
- `03-put-success.png` - Successful status update
- `04-customer-transaction.png` - Customer transactions
- `05-bad-request.png` - Validation error
- `06-h2-database.png` - H2 database verification
- `07-Junit-tests.png` - JUnit test results

## Project Structure

transaction-starter
│
├── src
│   ├── main
│   │   ├── java/com.example.transactionstarter
│   │   │   ├── TransactionController.java
│   │   │   ├── service/TransactionService.java
│   │   │   └── transaction
│   │   │       ├── Transaction.java
│   │   │       ├── TransactionRepository.java
│   │   │       ├── TransactionStatus.java
│   │   │       └── TransactionType.java
│   │   └── resources/application.properties
│   └── test/java - JUnit tests
├── screenshots/
├── pom.xml
├── mvnw / mvnw.cmd
└── README.md


## How to Run

### Using Eclipse / IntelliJ
1. Import the project as Maven Project.
2. Ensure Java 17 is configured.
3. Run the main Spring Boot Application class as `Java Application`.
4. App runs on `http://localhost:8080`

### Running Tests

*Windows:*
mvnw.cmd clean test

*Linux / macOS:*
./mvnw clean test

## Known Limitations

- H2 is in-memory, so data is lost on restart.
- Validation is implemented in the service layer.
- Error handling can be improved with custom exception classes and a Global Exception Handler.
- Authentication and authorization are out of scope.

## AI Usage Disclosure

ChatGPT was used during development to:
- Understand the assignment and Spring Boot concepts
- Review code and validation rules
- Suggest test scenarios
- Troubleshoot development issues
- Prepare project documentation

All suggestions were reviewed and adapted. The final application was verified using Eclipse, Postman, H2 Console, and JUnit.

## Conclusion

This project implements the required transaction operations using Spring Boot, JPA, and H2 with proper validation, business rules, exception handling, automated tests, and API verification.