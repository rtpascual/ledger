# Contributing to Ledger

First off, thank you for considering contributing to Ledger! It's people like you that make open source great.

## How to Contribute

1.  **Fork the repository:** Click the "Fork" button on the top right of the repository's page on GitHub.
2.  **Clone your fork:** `git clone https://github.com/YOUR_USERNAME/ledger.git`
3.  **Create a new branch:** `git checkout -b my-new-feature`
4.  **Make your changes:** Make your changes in your local repository.
5.  **Commit your changes:** `git commit -m 'Add some feature'`
6.  **Push to the branch:** `git push origin my-new-feature`
7.  **Create a new Pull Request:** Go to the original repository on GitHub and create a new pull request.

## Prerequisites

Before you begin, ensure you have the following installed:

*   [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or later
*   [Apache Maven](https://maven.apache.org/download.cgi)
*   [Node.js and npm](https://nodejs.org/en/download/)

## Local Development Setup

Follow these steps to get the application running locally.

### 1. Build the Core Module

First, you need to build the `core` module and install it into your local Maven repository. This will make it available as a dependency for the `backend` and `cli` modules.

```bash
cd core
mvn clean install
cd ..
```

### 2. Run the Backend (REST API)

Next, start the Spring Boot backend server.

```bash
cd backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 3. Run the Frontend (Web UI)

In a separate terminal, install the frontend dependencies and start the development server.

```bash
cd frontend
npm install
npm start
```

The web application will be available at `http://localhost:3000`.

### 4. Use the CLI

To use the command-line interface, you first need to build the executable JAR.

```bash
cd cli
mvn clean package
```

Once built, you can run commands using the generated JAR file from the `cli` directory.

```bash
# Example: View account balances
java -jar target/cli-1.0-SNAPSHOT.jar balance

# Example: View recent transactions
java -jar target/cli-1.0-SNAPSHOT.jar view

# Example: Add a transaction (will prompt for details)
java -jar target/cli-1.0-SNAPSHOT.jar add
```

## Submitting a Pull Request

When you're ready to submit a pull request, please make sure to:

*   Provide a clear and descriptive title and description for your pull request.
*   Reference any related issues in your pull request description.
*   Make sure your code follows the project's coding standards (see `ARCHITECTURE.md`).
*   Make sure all tests are passing.