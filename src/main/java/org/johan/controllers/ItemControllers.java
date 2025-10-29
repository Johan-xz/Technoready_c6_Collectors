package org.johan.controllers;

import org.johan.models.Item;
import org.johan.services.FilterService; 
import org.johan.services.ItemService;
import com.google.gson.Gson;
import java.util.List;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * ItemController
 * Handles all HTTP routes related to items and filtering.
 */
public class ItemControllers {

    private final ItemService itemService = new ItemService();
    private final FilterService filterService = new FilterService();
    private final Gson gson = new Gson();

    public ItemControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        get("/items", (req, res) -> {
            res.type("application/json");

            // Obtener parámetros de la query (siguen siendo String)
            String category = req.queryParams("category");
            String minPriceStr = req.queryParams("minprice");
            String maxPriceStr = req.queryParams("maxprice");
            // Podríamos añadir filtro de disponibilidad si quisiéramos
            // String availableStr = req.queryParams("available");

            // Convertir precios String a Double (manejar nulls)
            Double minPrice = null;
            if (minPriceStr != null && !minPriceStr.isEmpty()) {
                try {
                    minPrice = Double.parseDouble(minPriceStr);
                } catch (NumberFormatException e) {
                    // Opcional: Manejar error si el precio no es un número válido
                    System.err.println("Invalid minPrice format: " + minPriceStr);
                    // Podrías devolver un error 400 aquí
                }
            }

            Double maxPrice = null;
            if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
                try {
                    maxPrice = Double.parseDouble(maxPriceStr);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid maxPrice format: " + maxPriceStr);
                }
            }

            // Boolean available = availableStr != null ? Boolean.parseBoolean(availableStr) : null;

            // Obtener TODOS los items primero
            List<Item> allItems = itemService.getAllItems();

            // Aplicar filtros usando la INSTANCIA y pasando TODOS los argumentos
            // Pasamos null para 'available' ya que no lo estamos usando aún
            List<Item> filteredItems = filterService.applyFilters(allItems, category, minPrice, maxPrice, null);

            return gson.toJson(filteredItems); // Devolver la lista (filtrada o completa)
        });

        // Get an item by ID (sin cambios)
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

        // Add a new item (sin cambios)
        post("/items", (req, res) -> {
            res.type("application/json");
            Item newItem = gson.fromJson(req.body(), Item.class);
            // Podríamos añadir validación aquí antes de añadir
            Item createdItem = itemService.addItem(newItem);
            res.status(201);
            return gson.toJson(createdItem);
        });
    }

    // Simple internal response message class (sin cambios)
    private static class ResponseMessage {
        String message;
        public ResponseMessage(String msg) { this.message = msg; }
    }
}