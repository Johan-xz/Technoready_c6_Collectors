package org.johan.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Example UserController with routes for GET and POST. johan jpohan
 */
public class UserControllers {

    private final Gson gson = new Gson();
    private final List<Map<String, String>> users = new ArrayList<>();

    public UserControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        // GET /users → retorna todos los usuarios
        get("/users", (req, res) -> {
            return gson.toJson(users);
        });

        // POST /users → crea un nuevo usuario
        post("/users", (req, res) -> {
            Map<String, String> user = gson.fromJson(req.body(), Map.class);
            user.put("id", UUID.randomUUID().toString());
            users.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        // GET /users/:id → consulta un usuario por ID
        get("/users/:id", (req, res) -> {
            String id = req.params(":id");
            for (Map<String, String> user : users) {
                if (user.get("id").equals(id)) {
                    return gson.toJson(user);
                }
            }
            res.status(404);
            return gson.toJson(Map.of("message", "User not found"));
        });
    }
}