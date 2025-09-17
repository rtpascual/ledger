# Ledger Application Development Plan

This document outlines the step-by-step plan to develop the Ledger application, as described in `ARCHITECTURE.md`.

## Phase 0: Project Setup

- [x] Create the directory structure for the `core`, `backend`, `frontend`, and `cli` modules.
- [x] Initialize a git repository.

## Phase 1: Core Module

- [ ] **Setup Java Project:** Create a new Java project for the `core` module.
- [ ] **Define POJOs:** Create the Plain Old Java Objects for `Transaction`, `Account`, and `Posting` in `com.ledger.core.model`.
- [ ] **Implement DAO:**
    - [ ] Define the `LedgerDao` interface in `com.ledger.core.dao`.
    - [ ] Implement `LedgerDaoImpl` to handle read/write operations on the `.ledger` file.
- [ ] **Unit Testing:** Write unit tests for the DAO and any business logic in the core module.
- [ ] **Build:** Build the `core` module as a JAR file.

## Phase 2: Backend Module (Spring Boot)

- [ ] **Setup Spring Boot Project:** Create a new Spring Boot project for the `backend` module.
- [ ] **Add Core Dependency:** Add the `core` module as a dependency.
- [ ] **Create DTOs:** Create Data Transfer Objects in `com.ledger.api.dto`.
- [ ] **Implement Services:** Create a service layer in `com.ledger.api.service` to interact with the `core` module.
- [ ] **Implement Controllers:** Create Spring REST controllers in `com.ledger.api.controller` to expose the API endpoints.
- [ ] **API Endpoints:**
    - [ ] `GET /transactions`: View recent transactions.
    - [ ] `POST /transactions`: Add a new transaction.
    - [ ] `GET /balances`: View account balances.
- [ ] **Integration Testing:** Write integration tests for the REST API.

## Phase 3: CLI Module (Picocli)

- [ ] **Setup Java Project:** Create a new Java project for the `cli` module.
- [ ] **Add Core Dependency:** Add the `core` module as a dependency.
- [ ] **Implement Commands:**
    - [ ] Create Picocli command classes in `com.ledger.cli.commands`.
    - [ ] `add` command: Prompts for transaction details and uses the `core` module to save it.
    - [ ] `view` command: Uses the `core` module to display recent transactions.
    - [ ] `balance` command: Uses the `core` module to display account balances.
- [ ] **Main Class:** Create the `Main` class to run the Picocli application.
- [ ] **Build Executable:** Configure the build to create an executable JAR.

## Phase 4: Frontend Module (React)

- [ ] **Setup React Project:** Create a new React project with TypeScript for the `frontend` module.
- [ ] **API Services:** Create services in `src/services` to communicate with the backend REST API.
- [ ] **Type Definitions:** Create TypeScript types in `src/types` for the data received from the API.
- [ ] **Components:**
    - [ ] `TransactionForm`: A form to add new transactions.
    - [ ] `TransactionList`: A component to display a list of transactions.
    - [ ] `BalanceView`: A component to display account balances.
- [ ] **App Structure:**
    - [ ] Set up routing (if necessary).
    - [ ] Assemble the components in `App.tsx`.
- [ ] **Styling:** Apply basic styling to the components.

## Phase 5: Integration and Finalization

- [ ] **End-to-End Testing:** Perform manual end-to-end testing of the web application and the CLI.
- [ ] **Documentation:**
    - [ ] Update `README.md` with final build and run instructions.
    - [ ] Ensure `ARCHITECTURE.md` is up-to-date.
- [ ] **Code Quality:**
    - [ ] Run linters (Checkstyle, ESLint) and formatters (Prettier) on the entire codebase.
    - [ ] Ensure all tests are passing.
