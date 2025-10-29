package org.johan.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import org.johan.models.Item;
import org.johan.services.ItemService;
import org.johan.services.FilterService;
import java.util.List;

/**
 * ItemController
 * Handles all HTTP routes related to items and filtering.
 * Allows retrieving items, filtering by category/price, and getting details by ID.
 */
public class ItemControllers {

    private final ItemService itemService = new ItemService();
    private final FilterService filterService = new FilterService();
    private final Gson gson = new Gson();

    public ItemControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        // Get all items
        get("/items", (req, res) -> {
            res.type("application/json");
            String category = req.queryParams("category");
            String minPrice = req.queryParams("minprice");
            String maxPrice = req.queryParams("maxprice");

            // If no filters are provided, return all items
            if (category == null && minPrice == null && maxPrice == null) {
                return gson.toJson(itemService.getAllItems());
            } else {
                // Apply filters dynamically
                List<Item> filteredItems = FilterService.applyFilters(category, minPrice, maxPrice);
                return gson.toJson(filteredItems);
            }
        });

        // Get an item by ID
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

        // Add a new item
        post("/items", (req, res) -> {
            res.type("application/json");
            Item newItem = gson.fromJson(req.body(), Item.class);
            Item createdItem = itemService.addItem(newItem);
            res.status(201);
            return gson.toJson(createdItem);
        });
    }

    // Simple internal response message DTO
    private static class ResponseMessage {
        String message;
        public ResponseMessage(String msg) { this.message = msg; }
    }
}