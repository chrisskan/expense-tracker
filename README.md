# README.md

# Personal Expense Tracker

![Java](https://img.shields.io/badge/Java-17+-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green)
![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

A **Personal Expense Tracker** built with **Java Spring Boot** and **MongoDB**. 📝💰
Track your expenses by **business**, **category**, **amount**, and **date**.
Designed as a **portfolio project** to demonstrate clean architecture, REST API design, and unit testing.

---

## Features ⭐

* Add, retrieve, and delete expenses
* Query expenses by:

  * Business name (case-insensitive, partial match)
  * Category
  * Amount (≥ minimum)
  * Date range
* Layered architecture:

  * **Controller** → REST endpoints
  * **Service** → Business logic
  * **Repository** → MongoDB data access
* Unit tests for:

  * Repository queries
  * Service layer
  * Controller endpoints

---

## Architecture Diagram (UML) 📐

```text
+----------------+       +-------------------+       +--------------------+
| ExpenseController| ---> | ExpenseService    | ---> | ExpenseRepository   |
| (REST endpoints) |       | (Business logic)  |       | (MongoDB CRUD)     |
+----------------+       +-------------------+       +--------------------+
```

---

## Tech Stack 🛠️

* Java 17+
* Spring Boot 3+
* Spring Data MongoDB
* MongoDB (local or Docker)
* JUnit 5 + Mockito for testing
* Maven

---

## Project Structure 📂

```
src/main/java/com/example/expensetracker
├── controller
│   └── ExpenseController.java
├── model
│   └── Expense.java
├── repository
│   └── ExpenseRepository.java
├── service
│   ├── ExpenseService.java
│   └── ExpenseServiceImpl.java
```

```
src/test/java/com/example/expensetracker
├── controller
│   └── ExpenseControllerTest.java
├── repository
│   └── ExpenseRepositoryTest.java
└── service
    └── ExpenseServiceImplTest.java
```

---

## Sample Expense JSON 💾

```json
{
  "business": "CoffeeShop",
  "category": "Food",
  "amount": 3.50,
  "dateTime": "2025-09-16T10:34:15"
}
```

---

## Getting Started 🚀

### Prerequisites

* Java 17+
* Maven
* MongoDB running locally or via Docker

### Running the project

1. Clone the repo:

```bash
git clone https://github.com/yourusername/personal-expense-tracker.git
cd personal-expense-tracker
```

2. Start MongoDB (local or Docker):

```bash
docker run -d -p 27017:27017 --name mongo mongo:6
```

3. Build and run Spring Boot:

```bash
mvn clean spring-boot:run
```

4. The API will be available at:

```
http://localhost:8080/api/expenses
```

---

## API Endpoints 🌐

| Method | Endpoint                                            | Description              |
| ------ | --------------------------------------------------- | ------------------------ |
| POST   | `/api/expenses`                                     | Create an expense        |
| GET    | `/api/expenses`                                     | Get all expenses         |
| GET    | `/api/expenses/{id}`                                | Get expense by ID        |
| DELETE | `/api/expenses/{id}`                                | Delete expense           |
| GET    | `/api/expenses/search/business?fragment={text}`     | Search by business       |
| GET    | `/api/expenses/search/category?category={category}` | Search by category       |
| GET    | `/api/expenses/search/amount?minAmount={amount}`    | Search by minimum amount |
| GET    | `/api/expenses/search/date?from={from}&to={to}`     | Search by date range     |

---

## Testing 🧪

Run unit tests:

```bash
mvn test
```

* Repository queries
* Service logic
* Controller endpoints

---

## Notes 📝

* `amount` is stored as **Double**
* Built as a **portfolio project**, showcasing:

  * Spring Boot REST API
  * MongoDB integration
  * Layered architecture
  * Unit testing best practices

---

## License 📄

MIT License

