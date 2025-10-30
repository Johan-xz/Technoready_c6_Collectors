package org.johan;

import org.johan.controllers.ItemControllers;
import org.johan.controllers.OffersControllers;
import org.johan.controllers.UserControllers;

import static spark.Spark.before;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.threadPool;
import static spark.Spark.exception;
import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;
import org.johan.exceptions.ConflictException;
import org.johan.exceptions.UnauthorizedException;

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
// Indicar a Spark dónde encontrar los archivos estáticos (CSS, JS, imágenes, etc.)
spark.Spark.staticFiles.location("/public");

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
        // 4️⃣ Exception handling avanzado
        // ----------------------------
        exception(NotFoundException.class, (ex, req, res) -> {
            handleError(ex, req, res, 404);
        });
        exception(ValidationException.class, (ex, req, res) -> {
            handleError(ex, req, res, 400);
        });
        exception(ConflictException.class, (ex, req, res) -> {
            handleError(ex, req, res, 409);
        });
        exception(UnauthorizedException.class, (ex, req, res) -> {
            handleError(ex, req, res, 401);
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

    // Maneja errores, retorna JSON si request es API/rest, HTML si es web.
    private static void handleError(Exception ex, spark.Request req, spark.Response res, int statusCode) {
        res.status(statusCode);
        String accept = req.headers("Accept");
        String message = ex.getMessage();
        String path = req.pathInfo();
        boolean isWeb = path.contains("/web") || (accept != null && accept.contains("text/html"));
        if (!isWeb && accept != null && accept.contains("application/json")) {
            res.type("application/json");
            res.body("{\"error\":\"" + message.replaceAll("\"", "'" ) + "\"}");
        } else {
            res.type("text/html");
            res.body("<html><body><h2>Error: " + statusCode + "</h2><p>" + message + "</p></body></html>");
        }
    }
}