# Requirements Gathering for Lazy Ledger Backend

## Introduction

Lazy Ledger is a backend application designed to manage financial ledgers, providing APIs for transaction tracking, account management, and reporting. The system aims to be efficient, secure, and scalable, with a development timeline of one month.

This document outlines the functional and non-functional requirements for the backend development phase.

## Functional Requirements

### 1. User Management
- **FR1.1**: Allow user registration and authentication.
- **FR1.2**: Support user roles (e.g., admin, user).
- **FR1.3**: Provide JWT-based authentication.

### 2. Account Management
- **FR2.1**: Create, read, update, and delete accounts.
- **FR2.2**: Associate accounts with users.
- **FR2.3**: Support different account types (e.g., checking, savings).

### 3. Transaction Management
- **FR3.1**: Record income and expense transactions.
- **FR3.2**: Categorize transactions (e.g., food, transport).
- **FR3.3**: Support recurring transactions.
- **FR3.4**: Validate transaction data (e.g., balance checks).

### 4. Reporting
- **FR4.1**: Generate balance reports.
- **FR4.2**: Provide transaction history with filters.
- **FR4.3**: Export reports in CSV/PDF format.

### 5. API Endpoints
- **FR5.1**: RESTful API for all operations.
- **FR5.2**: Swagger/OpenAPI documentation.
- **FR5.3**: Pagination for list endpoints.

## Non-Functional Requirements

### 1. Performance
- **NFR1.1**: Response time < 200ms for 95% of requests.
- **NFR1.2**: Handle up to 1000 concurrent users.
- **NFR1.3**: Database queries optimized for speed.

### 2. Security
- **NFR2.1**: Data encryption at rest and in transit.
- **NFR2.2**: Input validation and sanitization.
- **NFR2.3**: Rate limiting to prevent abuse.

### 3. Scalability
- **NFR3.1**: Horizontal scaling support.
- **NFR3.2**: Database sharding if needed.
- **NFR3.3**: Microservices architecture consideration.

### 4. Reliability
- **NFR4.1**: 99.9% uptime.
- **NFR4.2**: Automated backups.
- **NFR4.3**: Error handling and logging.

### 5. Usability
- **NFR5.1**: Clear API responses.
- **NFR5.2**: Comprehensive error messages.
- **NFR5.3**: Easy integration with frontend.

### 6. Compatibility
- **NFR6.1**: JDK 21 compatibility.
- **NFR6.2**: PostgreSQL 15+ support.
- **NFR6.3**: Docker containerization.

## Timeline

Given the one-month development period:

- **Week 1**: Setup, authentication, user management.
- **Week 2**: Account and transaction CRUD.
- **Week 3**: Reporting, API documentation.
- **Week 4**: Testing, optimization, deployment.

## Assumptions

- Frontend development is separate.
- Database schema is finalized.
- Third-party integrations are minimal.

## Risks

- Tight timeline may affect testing.
- Security requirements may require additional time.
- Scalability needs may evolve.