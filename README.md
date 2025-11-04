# 🛍️ Collectors E-Commerce API

## 📚 Table of Contents

1. [Project Overview](#-project-overview)
2. [Tech Stack](#-tech-stack)
3. [Use Case Diagrams](#-use-case-diagrams)
4. [Proficient-Level Justification](#-proficient-level-justification)
5. [Stage 1 — Implemented Features](#-stage-1--implemented-features)
6. [Stage 2 — In Development](#-stage-2--in-development)
7. [Stage 3 — Upcoming](#-stage-3--upcoming)
8. [How to Run the Project](#-how-to-run-the-project)
9. [API Endpoints](#-api-endpoints)
10. [Repository Structure](#-repository-structure)

---

## 🧾 Project Overview

The **Collectors E-Commerce API** is a Java-based project designed to simulate an online platform where users can view, add, and manage collectible items and offers.  
Built using **Spark Java (microframework)**, it focuses on creating a lightweight RESTful API that supports full CRUD operations for users and items, including dynamic filtering, real-time updates, and robust error handling.

This project is divided into **three development stages (Sprints)**, progressively adding features, improving structure, and enhancing user experience through front-end integration and real-time functionalities.

---

## 🧠 Tech Stack

| Category             | Technology                          |
| -------------------- | ----------------------------------- |
| Programming Language | Java 17                             |
| Web Framework        | [Spark Java](http://sparkjava.com/) |
| Build Tool           | Maven                               |
| Data Serialization   | Gson                                |
| Logging              | Logback                             |
| Testing Framework    | JUnit 5                             |
| Version Control      | Git / GitHub                        |
| IDE Recommendation   | IntelliJ IDEA / VS Code             |

---

## 🧩 Use Case Diagrams

Users

- Create, read, update, and delete users.
- Retrieve user information by ID.
- Check if a user exists.

Items

- Manage and filter collectible items.
- Apply dynamic filters (category, price range, availability).
- Create and edit item details.
- View item offers and promotions.

Offers

- Manage promotional offers for items.
- Display discount percentage and validity date.
- Integrate with UI templates to display offers visually.

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

2. Maven Configuration & Routing:
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

- Configure Maven and core dependencies (Spark, Gson, Logback).
- Create API routes for users and items.
- Implement CRUD support:
  - GET /users
  - GET /users/:id
  - POST /users
  - PUT /users/:id
  - DELETE /users/:id
- Equivalent routes for /items.
- Establish repository structure and documentation.

Deliverables

- Fully working REST endpoints for Users and Items.
- README.md and technical documentation.
- GitHub repo initialized and shared with proper access.
- Configured pom.xml with spark-core, gson, and slf4j-simple (Logback).
- Implemented full CRUD API for users at path("/api", ...):
  GET /api/users
  GET /api/users/:id
  POST /api/users
  PUT /api/users/:id
  DELETE /api/users/:id
  OPTIONS /api/users/:id
- Established the 3-Tier architecture (models/, services/, controllers/).

## ⚙️ Stage 2 — Completed

Status: ✅ Completed

### Objectives

- Add exception handling module.
- Create Mustache templates and web forms for frontend representation.
- Implement routes for item offers and integrate them with templates.
- Conduct partial peer reviews to identify logic or integration errors.
- Push deliverables with consistent documentation.

### Deliverables

- ExceptionHandler.java for error responses, centralized exception module created.
- Offer and View templates (offers.mustache, etc.).
- Peer review notes (docs/peer-review.md).
- Updated repository structure with frontend assets.
- Web Forms:
  - OffersControllers.java created to manage item offers.
  - offers.mustache and offer-form.mustache templates created to display and submit offers.
  - styles.css created and served from the /public static file location.

## ⚡ Stage 3 — Completed

## Status: 🔜 Completed

### Objectives

Sprint 3 — Overview

Sprint 3 focused on moving the application from hard-coded sample data toward a maintainable, data-driven design and on completing full filtering and real-time update capabilities. The main goals were: (1) load initial items from a JSON resource, (2) centralize filter logic, and (3) add real-time price broadcasting.

Objectives
Replace hard-coded sample items with a classpath JSON resource (src/main/resources/data/items.json) to make initial data editable without recompiling.
Implement robust loading of JSON data with safe fallback to sample data.
Delegate all item filter logic (category, price range, availability) to a dedicated FilterService.
Ensure the front-end templates can display images and categories loaded from the JSON.
Prepare the app for real-time price updates via PriceWebSocket (broadcasting price updates to connected clients).

### What was implemented:

Data-driven initialization:
Added src/main/resources/data/items.json containing the initial items with fields: id, name, description, price, category, imageUrl, available.
Updated ItemService to load items from the classpath resource using Gson. If loading fails (missing file or parse errors), the service falls back to a small in-memory sample dataset so the site remains functional.
Filtering:
All filtering logic is handled by FilterService (single responsibility). The ItemService#getAllItems(...) method parses HTTP-style query values (strings) into the proper types (Double/Boolean) and delegates.
Images and categories:
JSON entries include imageUrl and category so mustache templates can render images and category labels exactly like the original hard-coded dataset.
Real-time updates:
PriceWebSocket is prepared to broadcast updates; integration points are available for other services (e.g., PriceUpdateService) to publish price changes to WebSocket clients.
Documentation:
Javadoc added to ItemService and a README update describing Sprint 3 changes and testing steps.

### How to test / QA checklist

 1. Build and run the app:
       mvn clean compile exec:java

2. Open the store page:
       http://localhost:4567/tienda
       Verify items are shown and images are displayed (images served from src/main/resources/public/images).

3. API verification:
- GET /api/items (or your equivalent endpoint) should return items consistent with data/items.json.
- Try query filters, e.g.:
- /api/items?categoria=Ropa
- -api/items?precioMin=300&precioMax=700
- /api/items?disponible=true

4. Negative / fallback test:
- Temporarily rename or remove data/items.json and restart the app. The server should fall back to the internal sample dataset and still serve items (useful for demos).

5. WebSocket smoke test:
- Open a WebSocket client (or the front-end page that connects) to the configured path (e.g., /precios).
Trigger a price update via the app (or via PriceUpdateService) and confirm clients receive the update broadcast.

6. Static assets check:
Confirm image filenames referenced in items.json exist under src/main/resources/public/images and load correctly.


### Acceptance criteria
- Items are loaded from data/items.json at startup when file is present and valid.
- When data/items.json is missing/invalid, the app uses the hard-coded fallback dataset and starts successfully.
- Filtering by category, price range, and availability returns the expected subsets.
- Templates display item images and categories matching the JSON data.
- WebSocket broadcasts are received by connected clients when prices change (end-to-end manual validation acceptable for demo).


### Implementation notes / developer hints

- JSON parsing uses Gson (already included in pom.xml). The ItemService contains a small helper internal type to map JSON fields to Item POJOs.

- Prices in the JSON are stored as strings like "$621.34 USD" to preserve formatting. The service converts these to numeric prices for filtering and arithmetic.
Keep the fallback sample for demo reliability, or remove it if you prefer a strict startup validation that fails when the JSON is missing.

- If you change the JSON structure, update the helper type and parsePrice logic accordingly.
Recommended future improvements: add unit tests for JSON loading and filtering, and expose an admin endpoint to reload the JSON without restarting.

## ▶️ How to Run the Project

### Clone the repository:

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

- Main Store: http://localhost:4567/tienda

<img width="2940" height="6530" alt="image" src="https://github.com/user-attachments/assets/472458b0-480e-4865-9ade-403364acada8" />

- Offers Page: http://localhost:4567/offers-web

<img width="2940" height="3082" alt="image" src="https://github.com/user-attachments/assets/a1dffcda-bf83-4acd-9753-37a350d9ca73" />

- User API: http://localhost:4567/api/users

<img width="2940" height="3438" alt="image" src="https://github.com/user-attachments/assets/067d3e59-e501-43ce-8b27-c07f4f8305db" />

⚡ Stage 3 — Filters & Real-Time WebSocket Integration

Status: ✅ Completed

This sprint fulfills the "Development of filters and WebSocket integration" criteria at a Proficient (C2) level.
We implemented these advanced features not just as technical functions, but as innovative elements (C2) that directly solve the core stakeholder (Ramón’s) needs for a dynamic, real-time store.

🧩 C2 Justification & Key Deliverables
🧠 C2 - Advanced Filters (A Diverse Process)

Code: Filtering logic was not hard-coded. We created a dedicated FilterService.java, establishing a “guide” (C2) for this functionality.

Strategy: ItemService.java now delegates all filtering logic to FilterService.
This use of a diverse process (C2) and the Single Responsibility Principle demonstrates a high-level architectural strategy.

Impact:
The /tienda route is fully functional, using ItemController to read query parameters (?categoria=...) and pass them to the service layer.

⚙️ C2 - Real-Time WebSocket (An Innovative Element)

Strategy:
This is the core innovative element (C2) of the project, solving the complex business need for real-time price updates.

Backend (Code):
PriceWebSocket.java was created to manage all client connections.
It maintains a thread-safe (ConcurrentLinkedQueue) list of all active sessions and includes a broadcast() method to instantly send new price updates to all connected users — a robust, high-level solution.

Frontend (Code):
tienda.mustache was updated with client-side JavaScript to:

Connect to the /precios WebSocket endpoint.

Send a new bid to the server using ws.send().

Listen for broadcasts using ws.onmessage and instantly update the item price in the HTML without a page reload.

## 🗺️ API Endpoints

- GET /api/users: Retrieves a list of all users.

- GET /api/users/:id: Retrieves a specific user by ID.

- POST /api/users: Creates a new user.

- PUT /api/users/:id: Updates an existing user.

- DELETE /api/users/:id: Deletes a user.

- GET /offers: Retrieves a list of all offers (API).

- GET /offers/:id: Retrieves a specific offer by ID (API).

- POST /offers: Creates a new offer (API).

## 🗂 Repository Structure

```text
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
    ├── Docker.md
    └── api_endpoints.md
```
