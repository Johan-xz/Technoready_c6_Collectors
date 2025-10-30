# 🛍️ Collectors E-Commerce API

## 📚 Table of Contents
1. [Project Overview](#-project-overview)
2. [Tech Stack](#-tech-stack)
3. [Dependencies](#-dependencies)
4. [Use Case Diagrams](#-use-case-diagrams)
5. [Stage 1 — Implemented Features](#-stage-1--implemented-features)
6. [Stage 2 — In Development](#-stage-2--in-development)
7. [Stage 3 — Upcoming](#-stage-3--upcoming)
8. [How to Run the Project](#-how-to-run-the-project)
9. [Repository Structure](#-repository-structure)

---

## 🧾 Project Overview

The **Collectors E-Commerce API** is a Java-based project designed to simulate an online platform where users can view, add, and manage collectible items and offers.  
Built using **Spark Java (microframework)**, it focuses on creating a lightweight RESTful API that supports full CRUD operations for users and items, including dynamic filtering, real-time updates, and robust error handling.

This project is divided into **three development stages (Sprints)**, progressively adding features, improving structure, and enhancing user experience through front-end integration and real-time functionalities.

---

## 🧠 Tech Stack

| Category | Technology |
|-----------|-------------|
| Programming Language | Java 17 |
| Web Framework | [Spark Java](http://sparkjava.com/) |
| Build Tool | Maven |
| Data Serialization | Gson |
| Logging | Logback |
| Testing Framework | JUnit 5 |
| Version Control | Git / GitHub |
| IDE Recommendation | IntelliJ IDEA / VS Code |

---

## 📦 Dependencies

Defined in the `pom.xml` file:

```xml
<dependencies>
    <!-- Web framework -->
    <dependency>
        <groupId>com.sparkjava</groupId>
        <artifactId>spark-core</artifactId>
        <version>2.9.4</version>
    </dependency>

    <!-- JSON parser -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>

    <!-- Logging -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.5.18</version>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
## 🧩 Use Case Diagrams
Users

    Create, read, update, and delete users.
    Retrieve user information by ID.
    Check if a user exists.

Items

    Manage and filter collectible items.
    Apply dynamic filters (category, price range, availability).
    Create and edit item details.
    View item offers and promotions.

Offers

    Manage promotional offers for items.
    Display discount percentage and validity date.
    Integrate with UI templates to display offers visually.

```
 ┌─────────────────────┐
 │       USER          │
 └────────┬────────────┘
          │
          ▼
   [ Create / View / Edit / Delete User ]
          │
          ▼
   [ Manage Items and Offers ]
          │
          ▼
   [ Filter Items by Criteria ]
```

## 🚀 Stage 1 — Implemented Features

Status: ✅ Completed
Objectives

    Configure Maven and core dependencies (Spark, Gson, Logback).
    Create API routes for users and items.
    Implement CRUD support:
        GET /users, GET /users/:id, POST /users, PUT /users/:id, DELETE /users/:id
        Equivalent routes for /items.
    Establish repository structure and documentation.

Deliverables

    Fully working REST endpoints for Users and Items.
    README.md and technical documentation.
    GitHub repo initialized and shared with proper access.

## ⚙️ Stage 2 — In Development

Status: 🚧 Ongoing
Objectives

    Add exception handling module.
    Create Mustache templates and web forms for frontend representation.
    Implement routes for item offers and integrate them with templates.
    Conduct partial peer reviews to identify logic or integration errors.
    Push deliverables with consistent documentation.

Deliverables

    ExceptionHandler.java for error responses.
    Offer and View templates (offers.mustache, etc.).
    Peer review notes (docs/peer-review.md).
    Updated repository structure with frontend assets.

## ⚡ Stage 3 — Upcoming

## Status: 🔜 Planned
Objectives

    Introduce a project checklist to validate all completed features.
    Add item filters (by category, price, availability).
    Implement WebSocket for real-time price updates.
    Perform final peer verification and documentation updates.
    Push deliverables and finalize project for submission.

Deliverables

    FilterService.java for item criteria.
    PriceWebSocket.java for live updates.
    Updated README.md and final report.
    Complete feature checklist (docs/checklist.md).

## ▶️ How to Run the Project

Clone the repository:

```bash

git clone https://github.com/<username>/Technoready_c6_Collectors.git
cd Technoready_c6_Collectors
```
Build and run using Maven:

```bash

mvn clean compile exec:java
```
Access API in your browser or Postman:

```text

http://localhost:4567/users
```
## 🗂 Repository Structure
``` text
Collectors_ecommerce/
│
├── pom.xml
├── README.md
│
├── src/
│   ├── main/java/org/johan/
│   │   ├── Main.java
│   │   ├── controllers/
│   │   │   ├── UserController.java
│   │   │   ├── ItemController.java
│   │   │   └── OfferController.java
│   │   ├── models/
│   │   │   ├── User.java
│   │   │   ├── Item.java
│   │   │   └── Offer.java
│   │   ├── services/
│   │   │   ├── UserService.java
│   │   │   ├── ItemService.java
│   │   │   ├── OfferService.java
│   │   │   └── FilterService.java
│   │   └── websocket/
│   │       └── PriceWebSocket.java
│   └── test/
│       └── java/org/johan/tests/
│           ├── ItemServiceTest.java
│           └── UserControllerTest.java
│
└── docs/
    ├── technical_report.md
    ├── peer-review.md
    ├── checklist.md
    └── api_endpoints.md
```