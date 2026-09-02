# Transaction Starter Project

This project implements a Customer Transactions REST API using Spring Boot,
Spring Data JPA, and an H2 embedded database.

## Technologies

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 embedded database
- JUnit 5
- Mockito
- Maven Wrapper

## Implemented Operations

The application implements these four operations:

1. Create transaction
2. Get transaction
3. Update transaction status
4. Get all transactions for a customer

## Transaction Fields

Every transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

## Validation Rules

The following validation rules are implemented:

- Transaction ID is required and must not be blank.
- Transaction ID must be unique.
- Customer ID is required and must not be blank.
- Amount is required and must be greater than zero.
- Currency is required and must not be blank.
- Transaction type is required.
- Transaction status is optional when creating a transaction.
- If the initial transaction status is not provided, it is automatically set to `PENDING`.

## Business Rules

The following business rules are implemented:

- A duplicate transaction ID cannot be created.
- A transaction must exist before its status can be updated.
- A `COMPLETED` transaction cannot be changed.
- A `FAILED` transaction cannot be changed.
- A status update requires a valid new status.
- A customer ID is required when retrieving customer transactions.

## Status Transition Rules

The following rules are applied when updating transaction status:

- `COMPLETED` transactions cannot be changed.
- `FAILED` transactions cannot be changed.
- Transactions that are not `COMPLETED` or `FAILED` can have their status updated.
- The new status must not be null.
- Newly created transactions use `PENDING` when no initial status is supplied.

## API

### 1. Create Transaction

**POST** `/api/transactions`

Creates a new transaction.

#### Example Request

```json
{
  "transactionId": "TXN001",
  "customerId": "CUST001",
  "amount": 1000.50,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "status": "PENDING"
}