package org.johan;

import static spark.Spark.*;

import org.johan.controllers.UserControllers;
import org.johan.controllers.ItemControllers;
import org.johan.controllers.OffersControllers;

/**
 * Main class - Entry point for Spark Java Web Server.
 * It configures the server, sets up routes via controllers,
 * and applies response formatting and CORS policies.
 */
public class Main {

    public static void main(String[] args) {

        // ----------------------------
        // 1️⃣ Server configuration
        // ----------------------------
        // Default port 4567 (you can override it with PORT environment variable)
        int portNumber = Integer.parseInt(System.getenv().getOrDefault("PORT", "4567"));
        port(portNumber);

        // Optional thread pool configuration
        threadPool(8, 2, 5000);

        // ----------------------------
        // 2️⃣ Enable CORS (for frontend testing)
        // ----------------------------
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });

        options("/*", (req, res) -> {
            res.status(200);
            return "OK";
        });

        // ----------------------------
        // 3️⃣ Initialize controllers (define all REST routes)
        // ----------------------------
        new UserControllers();   // /users endpoints
        new ItemControllers();   // /items endpoints
        new OffersControllers();  // /offers endpoints

        // ----------------------------
        // 4️⃣ Exception handling (optional)
        // ----------------------------
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Internal server error\"}";
        });

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Endpoint not found\"}";
        });

        // ----------------------------
        // 5️⃣ Confirmation message
        // ----------------------------
        System.out.println("✅ Spark Java server running on: http://localhost:" + portNumber);
        System.out.println("➡️  Available routes:");
        System.out.println("   GET  /users");
        System.out.println("   POST /users");
        System.out.println("   GET  /items");
        System.out.println("   GET  /offers");
        System.out.println("--------------------------------------------");
    }
}