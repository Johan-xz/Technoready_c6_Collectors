package org.johan.controllers;

import static spark.Spark.*;
import com.google.gson.Gson;
import org.johan.models.User;
import org.johan.services.UserService;

import java.util.List;

import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;

/**
 * Controller that manages all routes for Users.
 */
public class UserControllers {

    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    public UserControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        // GET /users → Retrieve list of all users
        get("/users", (req, res) -> {
            res.type("application/json");
            List<User> users = userService.getAllUsers();
            return gson.toJson(users);
        });

        // GET /users/:id → Retrieve user by ID
        get("/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            User user = userService.getUserById(id);

            if (user == null) {
                res.status(404);
                return gson.toJson(new ResponseMessage("User not found"));
            }

            return gson.toJson(user);
        });

        // POST /users → Add a new user
        post("/users", (req, res) -> {
            Map<String, String> user = gson.fromJson(req.body(), Map.class);
            // Validación simple
            if (user == null || user.get("name") == null || user.get("name").trim().isEmpty()) {
                throw new ValidationException("El nombre de usuario es obligatorio");
            }
            user.put("id", UUID.randomUUID().toString());
            users.add(user);
            res.status(201);
            return gson.toJson(newUser);
        });

        // PUT /users/:id → Edit an existing user
        put("/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            User updatedUser = gson.fromJson(req.body(), User.class);
            User result = userService.updateUser(id, updatedUser);

            if (result == null) {
                res.status(404);
                return gson.toJson(new ResponseMessage("User not found"));
            }
            throw new NotFoundException("User not found");
        });

        // OPTIONS /users/:id → Check whether a user with given ID exists
        options("/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            boolean exists = userService.getUserById(id) != null;
            return gson.toJson(new ResponseMessage("User exists: " + exists));
        });

        // DELETE /users/:id → Delete a specific user
        delete("/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            boolean deleted = userService.deleteUserById(id);

            if (!deleted) {
                res.status(404);
                return gson.toJson(new ResponseMessage("User not found or already deleted"));
            }

            return gson.toJson(new ResponseMessage("User deleted successfully"));
        });
    }

    // Simple internal class for readable responses
    private static class ResponseMessage {
        String message;
        ResponseMessage(String message) {
            this.message = message;
        }
    }
}