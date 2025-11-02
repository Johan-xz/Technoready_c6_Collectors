package org.johan.controllers;

import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;
import org.johan.models.User;
import org.johan.services.UserService;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class UserController {

    private final UserService userService;
    private  final Gson gson;

    public UserController(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    // GET /users
    public String getAllUsers(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(userService.getAllUsers());
    }

    // GET /users/:id (MODIFICADO)
    public String getUserById(Request req, Response res) {
        res.type("application/json");
        String id = req.params(":id");
        User user = userService.getUserById(id);
        
        if (user != null) {
            return gson.toJson(user);
        } else {
            // ¡LANZA LA EXCEPCIÓN! (C2)
            throw new NotFoundException("Usuario no encontrado con ID: " + id);
        }
    }
    
    // POST /users (MODIFICADO)
    public String createUser(Request req, Response res) {
        res.type("application/json");
        User newUser = gson.fromJson(req.body(), User.class);

        // (C2) Añadimos una validación simple
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            throw new ValidationException("El campo 'name' es obligatorio");
        }
        
        User createdUser = userService.createUser(newUser);
        res.status(201); // CREATED
        return gson.toJson(createdUser);
    }

    // PUT /users/:id (MODIFICADO)
    public String updateUser(Request req, Response res) {
        res.type("application/json");
        String id = req.params(":id");
        User updatedUserData = gson.fromJson(req.body(), User.class);
        User updatedUser = userService.updateUser(id, updatedUserData);

        if (updatedUser != null) {
            return gson.toJson(updatedUser);
        } else {
            // ¡LANZA LA EXCEPCIÓN! (C2)
            throw new NotFoundException("Usuario no encontrado para actualizar");
        }
    }

    // DELETE /users/:id (MODIFICADO)
    public String deleteUser(Request req, Response res) {
        res.type("application/json");
        String id = req.params(":id");
        
        if (userService.deleteUser(id)) {
            res.status(204); // No Content
            return "";
        } else {
            // ¡LANZA LA EXCEPCIÓN! (C2)
            throw new NotFoundException("Usuario no encontrado para eliminar");
        }
    }
    
    // OPTIONS /users/:id (MODIFICADO)
    public String checkUserExists(Request req, Response res) {
        String id = req.params(":id");
        if (userService.userExists(id)) {
            res.status(200);
            return "{\"status\":\"User exists\"}";
        } else {
            // ¡LANZA LA EXCEPCIÓN! (C2)
            throw new NotFoundException("Usuario no encontrado");
        }
    }
}