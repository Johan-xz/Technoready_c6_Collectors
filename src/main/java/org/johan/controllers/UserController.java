package org.johan.controllers;

import java.util.HashMap;
import java.util.Map;

import org.johan.exceptions.ConflictException;
import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;
import org.johan.models.User;
import org.johan.services.UserService;

import com.google.gson.Gson;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

public class UserController {

    private final UserService userService;
    private final Gson gson;
    private final MustacheTemplateEngine mustacheEngine;

    public UserController(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
        this.mustacheEngine = new MustacheTemplateEngine();
    }
    
    // GET /users-web - Renderiza la vista HTML de usuarios
    public String renderUsers(Request req, Response res) {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", "Users");
        model.put("activeUsers", true);
        model.put("users", userService.getAllUsers());
        return mustacheEngine.render(new ModelAndView(model, "users.mustache"));
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
    
    // POST /users/:id - Sprint 1: Según requisitos, POST debe incluir el ID en la ruta
    public String createUser(Request req, Response res) {
        res.type("application/json");
        String id = req.params(":id");
        User newUser = gson.fromJson(req.body(), User.class);
        
        // Asignar el ID de la ruta al usuario
        if (newUser.getId() == null) {
            newUser.setId(id);
        } else if (!newUser.getId().equals(id)) {
            throw new ValidationException("El ID en el body no coincide con el ID de la ruta");
        }

        // Validación de campos obligatorios
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            throw new ValidationException("El campo 'name' es obligatorio");
        }
        
        // Verificar si el usuario ya existe
        if (userService.userExists(id)) {
            throw new ConflictException("Ya existe un usuario con el ID: " + id);
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