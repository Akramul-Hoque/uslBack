# Loan Management API Documentation

## Overview
This API manages the full lifecycle of loans from creation to EMI tracking and closure.

## Base URL
`http://localhost:8080/api`

## Endpoints

### 1. Create a Loan
**POST** `/loans`

Creates a new loan with automatic EMI calculation.

**Request Body:**
```json
{
  "customerName": "John Doe",
  "principalAmount": 10000.00,
  "interestRate": 12.5,
  "tenureMonths": 12
}
```

**Response:**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "principalAmount": 10000.00,
  "interestRate": 12.5,
  "tenureMonths": 12,
  "status": "ACTIVE",
  "createDate": "2026-02-01",
  "totalExpectedEmi": 10661.50
}
```

### 2. Get All Loans
**GET** `/loans?page=0&size=10`

Retrieves all loans with pagination support.

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "customerName": "John Doe",
      "principalAmount": 10000.00,
      "interestRate": 12.5,
      "tenureMonths": 12,
      "status": "ACTIVE",
      "createDate": "2026-02-01",
      "totalExpectedEmi": 10661.50
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true
    },
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true
}
```

### 3. Add Payment
**POST** `/payments`

Adds a payment for a loan and updates the loan status accordingly.

**Request Body:**
```json
{
  "loanId": 1,
  "amountPaid": 1000.00
}
```

**Response:**
```json
{
  "id": 1,
  "loanId": 1,
  "amountPaid": 1000.00,
  "paymentDate": "2026-02-01"
}
```

### 4. Get Loan Summary
**GET** `/loans/{id}/summary`

Returns detailed loan information including total paid and remaining balance.

**Response:**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "principalAmount": 10000.00,
  "interestRate": 12.5,
  "tenureMonths": 12,
  "status": "ACTIVE",
  "createDate": "2026-02-01",
  "totalExpectedEmi": 10661.50,
  "totalPaid": 1000.00,
  "remainingBalance": 9661.50
}
```

## Business Rules

1. **EMI Calculation**: When a loan is created, the system pre-calculates and stores the total expected EMI amount using the standard EMI formula.

2. **Payment Processing**: Each payment updates the loan's remaining balance.

3. **Loan Closure**: If total paid >= total expected, the loan status is automatically marked as "CLOSED".

4. **Default Status**: If payment is delayed beyond the due date (1 month from creation), the loan status is marked as "DEFAULTED".

## Loan Status Values

- **ACTIVE**: Loan is currently active and accepting payments
- **CLOSED**: Loan has been fully paid off
- **DEFAULTED**: Loan has missed payment deadline

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- **400 Bad Request**: Validation errors or business rule violations
- **404 Not Found**: Loan not found
- **500 Internal Server Error**: Server-side errors

## H2 Database Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:mem:loandb`
- **Username**: `sa`
- **Password**: `password`

## Example Usage

### Create a loan:
```bash
curl -X POST http://localhost:8080/api/loans \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Alice Smith",
    "principalAmount": 50000,
    "interestRate": 10.5,
    "tenureMonths": 24
  }'
```

### Add a payment:
```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -d '{
    "loanId": 1,
    "amountPaid": 2500
  }'
```

### Get loan summary:
```bash
curl http://localhost:8080/api/loans/1/summary
```
