# 🐳 Docker Guide — Build & Run the Application

# 📚 Table of Contents
1. [Requirements](#-requirements)
2. [Project structure](#-project-structure)
    - [Build the Application Locally (Optional)](#-step-1--build-the-application-locally-optional)
    - [Build the Docker image](#-step-2--build-the-docker-image)
    - [Run the container](#-step-3--run-the-container)
3. [Access the application](#-access-the-application)

This guide explains how to build and run the Collectors_Ecommerce (Spark Java) project inside a Docker container.
You can follow these steps using IntelliJ IDEA, VS Code, or Docker Desktop / CLI.
## ⚙️ Requirements

Make sure you have the following tools installed:

1. Docker Desktop (for Windows/Mac)
2. Docker Engine (for Linux) 
3. Maven 3.8+ 
4. Java 17 (Eclipse Temurin 17 LTS recommended)

Optional editors:

- IntelliJ IDEA (Ultimate or Community) 
- VS Code with Docker & Java extensions

## 🧱 Project Structure

```text

Collectors_ecommerce/
├── Dockerfile
├── pom.xml
├── .dockerignore
├── src/
│   └── main/
│       ├── java/
│       │   └── org/johan/
│       │       ├── Main.java
│       │       └── controllers/...
│       └── resources/
│           ├── templates/
│           │   ├── items.mustache
│           │   └── offers.mustache
│           └── public/
```
## 🚀 Step 1 — Build the Application Locally (Optional)

From the root of the project, ensure the JAR can be built:

```bash

mvn clean package
```
Maven should generate a fat JAR (self‑contained) inside the target/ folder:

```text

target/tienda-coleccionables-1.0-SNAPSHOT-shaded.jar
```
## 🐳 Step 2 — Build the Docker Image
### 🔹 Command‑line (CLI)

From your terminal run:

```bash

docker build -t collectors_ecommerce .
```
Flag Description:

"-t collectors_ecommerce" Assigns a name (“tag”) to your image
.	Uses the Dockerfile from the current directory

### 🔹 IntelliJ IDEA

1. Open the project.
2. In Project View, right‑click your Dockerfile.
3. Choose “Run Dockerfile” → “Run with Docker”.
4. IntelliJ will build the image and display it in the Services panel under Images.

### 🔹 VS Code

1. Open the folder containing your project.
2. Install the Docker Extension (if not already).
3. Right‑click on your Dockerfile → “Build Image…”.
4. Name it collectors_ecommerce.

## 📦 Step 3 — Run the Container
### 🔹 CLI

```bash

docker run -d --name collectors_container -p 4567:4567 collectors_ecommerce
```
Flag Meaning:

"-d"	Detached mode (runs in background)

"--name"	Assigns a name to the container

"-p 4567:4567"	Maps port 4567 from container to your local port 4567

"collectors_ecommerce"	The image to run

---

## 🧭 Access the Application

Once the container is running, open your browser and go to:

👉 http://localhost:4567

🧪 Check Logs

See the app’s runtime logs with:

```bash

docker logs -f collectors_container
```

Expected output:
```text

[INFO] Spark Java server running on http://localhost:4567
[INFO] Available routes:
GET /users
POST /users
GET /items-web
GET /offers-web
```