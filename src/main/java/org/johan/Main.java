package org.johan;

import org.johan.controllers.ItemController;
import org.johan.controllers.OffersControllers;
import org.johan.controllers.UserController;
import org.johan.exceptions.ConflictException;
import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;
import org.johan.models.ErrorResponse;
import org.johan.services.ItemService;
import org.johan.services.UserService;
import org.johan.websocket.PriceWebSocket;

import com.google.gson.Gson;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.init;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

public class Main {
    public static void main(String[] args) {
        
        // --- 1. CONFIGURACIÓN DEL SERVIDOR ---
        port(4567);
        staticFiles.location("/public"); 

        // --- 2. REGISTRO DE WEBSOCKET (DEBE IR ANTES QUE TODO) ---
        // ¡Este es el cambio! Lo movimos aquí arriba.
        webSocket("/precios", PriceWebSocket.class);

        // --- 3. INYECCIÓN DE DEPENDENCIAS ---
        Gson gson = new Gson();
        UserService userService = new UserService();
        ItemService itemService = new ItemService();
        UserController userController = new UserController(userService, gson);
        ItemController itemController = new ItemController(itemService);
        
        // --- 4. REGISTRO DE RUTAS (CONTROLADORES Y EXCEPCIONES) ---
        
        // "Encender" el controlador de ofertas
        new OffersControllers(); 

        // Manejadores de Excepciones
        exception(NotFoundException.class, (e, req, res) -> {
            res.status(404);
            res.type("application/json");
            res.body(gson.toJson(new ErrorResponse("NOT_FOUND", e.getMessage())));
        });
        exception(ValidationException.class, (e, req, res) -> {
            res.status(400);
            res.type("application/json");
            res.body(gson.toJson(new ErrorResponse("BAD_REQUEST", e.getMessage())));
        });
        exception(ConflictException.class, (e, req, res) -> {
            res.status(409);
            res.type("application/json");
            res.body(gson.toJson(new ErrorResponse("CONFLICT", e.getMessage())));
        });
        exception(Exception.class, (e, req, res) -> {
            e.printStackTrace();
            res.status(500);
            res.type("application/json");
            res.body(gson.toJson(new ErrorResponse("SERVER_ERROR", "Ocurrió un error inesperado")));
        });

        // Rutas de API (Sprint 1)
        path("/api", () -> {
            path("/users", () -> {
                get("", userController::getAllUsers);
                // Aquí faltaban tus otras rutas de user, las añado:
                get("/:id", userController::getUserById);
                post("", userController::createUser); 
                put("/:id", userController::updateUser);
                options("/:id", userController::checkUserExists);
                delete("/:id", userController::deleteUser);
            });
        });

        // Rutas WEB (Sprint 2)
        get("/tienda", itemController::renderTienda);
        post("/items", itemController::createItem);

        // --- 5. INICIAR SERVIDOR (DEBE IR AL FINAL) ---
        // init() siempre debe ir después de definir TODAS las rutas.
        init(); 

        System.out.println("Servidor Spark iniciado en http://localhost:4567");
    }
}