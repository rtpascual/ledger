# Ledger Application

This is a personal finance management tool using plain text accounting principles. It features a Java backend, a React frontend, and a Java-based CLI.

For a detailed explanation of the project's architecture, components, and design decisions, please see [ARCHITECTURE.md](ARCHITECTURE.md).

## Usage

There are two ways to use the Ledger application:

*   **Web Application:** A user-friendly web interface for managing your finances.
*   **Command-Line Interface (CLI):** A powerful command-line tool for advanced users.

### Web Application

To run the web application, you need to have Java and Node.js installed.

1.  **Run the Backend:**
    ```bash
    cd backend
    mvn spring-boot:run
    ```
    The backend API will be running at `http://localhost:8080`.

2.  **Run the Frontend:**
    In a new terminal window:
    ```bash
    cd frontend
    npm install
    npm start
    ```
    The web application will be available at `http://localhost:3000`.

### Command-Line Interface (CLI)

The CLI allows you to manage your ledger from the command line.

1.  **Build the CLI:**
    ```bash
    cd cli
    mvn clean package
    ```

2.  **Run Commands:**
    Once built, you can run commands using the generated JAR file from the `cli` directory.

    *   **View account balances:**
        ```bash
        java -jar target/cli-1.0-SNAPSHOT.jar balance
        ```
    *   **View recent transactions:**
        ```bash
        java -jar target/cli-1.0-SNAPSHOT.jar view
        ```
    *   **Add a transaction:**
        ```bash
        java -jar target/cli-1.0-SNAPSHOT.jar add
        ```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for more information on how to contribute to the project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
