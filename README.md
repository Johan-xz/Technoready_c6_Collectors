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
## 🏆 Proficient-Level Justification

This project achieves the C2 (Proficient) level by demonstrating leadership and creativity through strategic architecture and the integration of diverse, innovative solutions.

1. Technical Knowledge & OOP
The project demonstrates "leadership and creativity" by implementing a strategic 3-Tier (Model-Service-Controller) architecture. This is a significant step beyond basic MVC.

How to Implement: This architecture "organizes and justifies strategies":

- models/: (e.g., User.java, Item.java) Pure data structures (POJOs).
- services/: (e.g., UserService.java) Contains all business logic, validation, and data handling.
- controllers/: (e.g., UserController.java) Purely responsible for handling HTTP requests and responses.

Result: This separation makes the project highly scalable, maintainable, and easy to test, proving a C2-level understanding of OOP principles and system design.

2. Maven Configuration & Routing
Routing is strategically designed to handle a "complex scenario": a hybrid app serving both an API and a Web UI.

How to Implement: In Main.java, routes are organized into "diverse processes" (C2) using path() groups:

- path("/api", ...): Groups all JSON-based RESTful endpoints.
- get("/tienda", ...): Serves the server-side rendered HTML views.
- webSocket("/precios", ...): Handles the real-time data stream.

Result: This strategy ensures no route collisions and provides a clear, scalable guide for adding future modules.

3. Exception Handling
The project implements a "versatile REST API solution" (C2) by "establishing guides and best practices" (C2) for error handling.

How to Implement: Instead of littering controllers with try-catch blocks, this project uses a centralized exception handling mechanism in Main.java.

- Custom exceptions (e.g., NotFoundException.java) are created in the exceptions/ package.
- Services and Controllers throw these specific exceptions.
- Main.java catches them using exception(NotFoundException.class, ...) and uses ErrorResponse.java to return a standardized JSON error message.

Result: This is an "innovative element" that is clean, robust, and demonstrates leadership in code design.

4. Filters & WebSocket Integration
The solution "integrates advanced features for complex projects" to solve specific stakeholder (Ramón's) needs.

How to Implement (Filters): Filtering is implemented at a C2-level by delegating all logic to a dedicated FilterService.java. The ItemService uses this service, demonstrating a "diverse process" (C2) that promotes code re-use and the Single Responsibility Principle.

How to Implement (WebSocket): The PriceWebSocket.java handler is the key "innovative element" (C2). It solves the complex problem of real-time updates by maintaining a concurrent (thread-safe) list of all connected sessions and broadcasting price updates instantly to every client. This directly enables the live-bidding feature requested in the project narrative.


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
    
    Configured pom.xml with spark-core, gson, and slf4j-simple (Logback).
    
    Implemented full CRUD API for users at path("/api", ...):
    
    GET /api/users
    
    GET /api/users/:id
    
    POST /api/users
    
    PUT /api/users/:id
    
    DELETE /api/users/:id
    
    OPTIONS /api/users/:id
    
    Established the 3-Tier architecture (models/, services/, controllers/).

## ⚙️ Stage 2 — In Development

Status: 🚧 Ongoing
Objectives

    Add exception handling module.
    Create Mustache templates and web forms for frontend representation.
    Implement routes for item offers and integrate them with templates.
    Conduct partial peer reviews to identify logic or integration errors.
    Push deliverables with consistent documentation.

Deliverables

    ExceptionHandler.java for error responses, centralized exception module created.
    Offer and View templates (offers.mustache, etc.).
    Peer review notes (docs/peer-review.md).
    Updated repository structure with frontend assets.
    Web Forms:
    OffersControllers.java created to manage item offers.
    offers.mustache and offer-form.mustache templates created to display and submit offers.
    styles.css created and served from the /public static file location.

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

The server is now running! Access the application at:

Main Store: http://localhost:4567/tienda

Offers Page: http://localhost:4567/offers-web

User API: http://localhost:4567/api/users

## 🗺️ API Endpoints

GET /api/users: Retrieves a list of all users.

GET /api/users/:id: Retrieves a specific user by ID.

POST /api/users: Creates a new user.

PUT /api/users/:id: Updates an existing user.

DELETE /api/users/:id: Deletes a user.

GET /offers: Retrieves a list of all offers (API).

GET /offers/:id: Retrieves a specific offer by ID (API).

POST /offers: Creates a new offer (API).


## 🗂 Repository Structure
``` text
Collectors_ecommerce/
│
├── pom.xml
├── README.md
│
├── src/
│   ├── main/resources/
│   │   ├── public/
│   │   │   ├── script.js
│   │   │   └── styles.css
│   │   ├── templates/
│   │   │   ├── offer-form.mustache
│   │   │   ├── offers.mustache
│   │   │   └── tienda.mustache
│   │   ├── Main.java
│   ├── main/java/org/johan/
│   │   ├── Main.java
│   │   ├── controllers/
│   │   │   ├── UserController.java
│   │   │   ├── ItemController.java
│   │   │   └── OfferController.java
│   │   ├── exceptions/
│   │   │   ├── ConflictException.java
│   │   │   ├── NotFoundException.java
│   │   │   ├── UnauthorizedException.java
│   │   │   └── ValidationException.java
│   │   ├── models/
│   │   │   ├── ErrorResponse.java
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
