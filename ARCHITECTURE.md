# Ledger CLI and Web Application

This document serves as a comprehensive guide to the Ledger project, outlining its architecture, design decisions, and usage. It is intended for developers working on the project and anyone interested in understanding its inner workings.

## 1. Overview

The Ledger application is a personal finance management tool that combines a powerful Java backend with a modern React frontend. It provides both a Java-based command-line interface (CLI) built with Picocli, and a web-based user interface for viewing and adding financial transactions.

The core of the application is built around the principles of plain text accounting and double-entry bookkeeping, using a `.ledger` file as the single source of truth for all transaction data. This approach ensures data integrity, longevity, and transparency.

### Core Features:

*   **Double-Entry Bookkeeping:** All transactions are recorded using the double-entry method, ensuring that the books are always balanced.
*   **Plain Text Data Storage:** Transactions are stored in a human-readable `.ledger` file, allowing for easy inspection, editing, and version control.
*   **REST API:** A Spring-based REST API provides a clear and robust interface between the backend and the web client.
*   **Web and CLI Interfaces:** Users can interact with their financial data through a user-friendly React web application or a powerful Java-based command-line interface.
*   **Personal Finance Management:** The application is designed to help users track their income, expenses, and account balances.

## 2. Project Architecture and Design

The application is designed with a modular architecture to promote code reuse and separation of concerns. It consists of a shared `core` module, a `backend` for the REST API, a `cli` for command-line interaction, and a `frontend` for the web UI.

### 2.1. High-Level Architecture

```
   +------------------+      +------------------+
   |   React Client   |----->|  Java REST API   |
   |      (Web)       |      |  (Spring Boot)   |
   +------------------+      +-------+----------+
                                     |
   +------------------+      +-------v----------+      +----------------+
   | Picocli Client   |----->|   Java Core      |----->|  .ledger file  |
   | (Java CLI)       |      | (Shared Logic)   |<-----|  (Data Store)  |
   +------------------+      +------------------+      +----------------+
```

*   The **Java Core** module contains all the business logic, domain models (POJOs), and data access objects (DAOs) for interacting with the `.ledger` file.
*   The **Java REST API** is a Spring Boot application that depends on the `core` module. It exposes the application's functionality to the web client.
*   The **Picocli Client** is a standalone command-line application that also depends on the `core` module to execute its commands.
*   The **React Client** is a web-based GUI that consumes the REST API.

### 2.2. Core (Shared Java Logic)

This module is the heart of the application and contains all the logic for handling the ledger data. It is a standard Java library (JAR) that can be included as a dependency in other modules.

*   **POJOs (Plain Old Java Objects):** Located in `com.ledger.core.model`, these represent the core domain objects like `Transaction`, `Account`, and `Posting`.
*   **DAOs (Data Access Objects):** Located in `com.ledger.core.dao`, this layer abstracts the data persistence. The `LedgerDao` interface and its `LedgerDaoImpl` implementation handle all read/write operations to the `.ledger` file.

### 2.3. Backend (Java/Spring REST API)

The backend is a Spring Boot application that provides the REST API for the web frontend. It uses the `core` module as a dependency to access the application's data and business logic.

*   **Controllers:** Spring REST controllers handle incoming HTTP requests, delegate to service layers, and return DTOs.
*   **Services:** The service layer acts as a bridge between the controllers and the `core` module.
*   **DTOs (Data Transfer Objects):** DTOs are used to transfer data between the backend and the frontend.

### 2.4. Frontend (TypeScript/React)

The frontend is a single-page application (SPA) built with TypeScript and React. It is a pure client-side application that communicates with the backend via the REST API.

### 2.5. CLI (Java/Picocli)

The CLI is a standalone Java application built using the Picocli framework. It provides a set of commands for interacting with the ledger data directly from the command line. It uses the `core` module as a dependency to execute its commands.

### 2.6. Data Storage (.ledger file)

The use of a `.ledger` file as the data store remains a key design feature, managed exclusively by the `core` module.

The format of the `.ledger` file is as follows:

```
YYYY-MM-DD <Description>
    <Account 1>    <Amount 1>
    <Account 2>    <Amount 2>
    ...
```

**Example:**

```
2024-07-29 Coffee Shop
    Expenses:Coffee    $5.00
    Assets:Checking
```

## 3. Folder Structure

### 3.1. Core (`/core`)

This directory contains the shared Java library.

```
core/
└── src/
    └── main/
        └── java/
            └── com/
                └── ledger/
                    └── core/
                        ├── dao/          // Data Access Object interfaces and impls
                        └── model/        // POJO domain models
```

### 3.2. Backend (`/backend`)

This directory contains the Spring Boot application. It will have a dependency on the `core` module.

```
backend/
└── src/
    └── main/
        └── java/
            └── com/
                └── ledger/
                    └── api/
                        ├── controller/   // Spring REST Controllers
                        ├── dto/          // Data Transfer Objects
                        └── service/      // Business logic services
```

### 3.3. Frontend (`/frontend`)

This directory contains the React web application.

```
frontend/
├── public/
└── src/
    ├── components/     // Reusable React components
    ├── hooks/          // Custom React hooks
    ├── services/       // API communication services
    ├── types/          // TypeScript type definitions
    ├── App.tsx
    └── index.tsx
```

### 3.4. CLI (`/cli`)

This directory contains the Picocli command-line application. It will have a dependency on the `core` module.

```
cli/
└── src/
    └── main/
        └── java/
            └── com/
                └── ledger/
                    └── cli/
                        ├── commands/   // Picocli command classes
                        └── Main.java
```

## 4. Design Decisions

| Decision                               | Pros                                                                                                                            | Cons                                                                                                                            |
| -------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| **Shared Core Module**                 | Reduces code duplication between the backend and CLI. Centralizes business logic, improving maintainability and consistency.      | Adds a small amount of complexity to the build process due to the inter-module dependency.                                      |
| **Java/Spring for Backend**            | Robust, scalable, and well-suited for enterprise-level applications. Large ecosystem and strong community support.                | Can be more verbose and have a steeper learning curve compared to some other backend technologies (e.g., Node.js, Python).      |
| **React/TypeScript for Frontend**      | Component-based architecture promotes reusability. Strong typing with TypeScript improves code quality and maintainability.       | Can be complex to set up and configure. State management can be challenging in large applications.                            |
| **Java/Picocli for CLI**               | Excellent for building native executables with GraalVM. Strong performance and access to the rich Java ecosystem.                 | Larger memory footprint compared to CLI tools written in languages like Go or Rust.                                           |
| **Plain Text Accounting (.ledger file)** | Data is transparent, portable, and easily version-controlled. Avoids vendor lock-in.                                            | Can be less performant for very large datasets compared to a traditional database. Requires custom parsing logic.             |

## 5. Use Cases

The use cases remain the same, but the implementation will now leverage the `core` module for all interactions with the `.ledger` file.

### 5.1. Add a New Transaction

*   **CLI (Picocli):** The `ledger add` command will use the `core` module to append the new transaction.
*   **Web UI:** The backend will receive the request and use the `core` module to append the new transaction.

### 5.2. View Recent Transactions

*   **CLI (Picocli):** The `ledger view` command will use the `core` module to read transactions.
*   **Web UI:** The backend will use the `core` module to read transactions and send them to the frontend.

### 5.3. View Account Balances

*   **CLI (Picocli):** The `ledger balance` command will use the `core` module to calculate balances.
*   **Web UI:** The backend will use the `core` module to calculate balances and send them to the frontend.

## 7. Error Handling

- **Backend:** The REST API will use Spring's `@ControllerAdvice` to handle exceptions and return appropriate HTTP status codes and error messages.
- **Frontend:** The React application will use error boundaries to catch and handle rendering errors. API call errors will be handled in the services layer.
- **CLI:** The CLI will use try-catch blocks to handle exceptions and provide user-friendly error messages.

## 8. Logging

- **Backend:** The Spring Boot application will use SLF4J for logging. Logs will be configured to output to both the console and a file.
- **CLI:** The CLI will use a simple logging framework to log important events and errors.
