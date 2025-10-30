package org.johan.controllers;

import static spark.Spark.*;
import com.google.gson.Gson;
import org.johan.models.Item;
import org.johan.services.ItemService;
import java.util.List;

/**
 * Controller that manages all routes for Items.
 */
public class ItemControllers {

    private final ItemService itemService = new ItemService();
    private final Gson gson = new Gson();

    public ItemControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        // GET /items → Retrieve all items
        get("/items", (req, res) -> {
            res.type("application/json");
            List<Item> items = itemService.getAllItems();
            return gson.toJson(items);
        });

        // GET /items/:id → Retrieve item by ID
        get("/items/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            Item item = itemService.getItemById(id);

            if (item == null) {
                res.status(404);
                return gson.toJson(new ResponseMessage("Item not found"));
            }

            return gson.toJson(item);
        });

        // POST /items → Add a new item
        post("/items", (req, res) -> {
            res.type("application/json");
            Item newItem = gson.fromJson(req.body(), Item.class);
            itemService.addItem(newItem);
            res.status(201);
            return gson.toJson(newItem);
        });

        // PUT /items/:id → Edit an existing item
        put("/items/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            Item updatedItem = gson.fromJson(req.body(), Item.class);
            Item result = itemService.updateItem(id, updatedItem);

            if (result == null) {
                res.status(404);
                return gson.toJson(new ResponseMessage("Item not found"));
            }

            return gson.toJson(result);
        });

        // OPTIONS /items/:id → Check whether an item with the given ID exists
        options("/items/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            boolean exists = itemService.getItemById(id) != null;
            return gson.toJson(new ResponseMessage("Item exists: " + exists));
        });

        // DELETE /items/:id → Delete a specific item
        delete("/items/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            boolean deleted = itemService.deleteItemById(id);

            if (!deleted) {
                res.status(404);
                return gson.toJson(new ResponseMessage("Item not found or already deleted"));
            }

            return gson.toJson(new ResponseMessage("Item deleted successfully"));
        });
    }

    // Helper message class for JSON responses
    private static class ResponseMessage {
        String message;
        ResponseMessage(String message) {
            this.message = message;
        }
    }
}