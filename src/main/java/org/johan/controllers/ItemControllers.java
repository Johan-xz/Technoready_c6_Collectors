package org.johan.controllers;

import org.johan.models.Item;
import org.johan.services.FilterService;
import org.johan.services.ItemService;
import com.google.gson.Gson;

// Imports para Mustache
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

// Imports de Spark (asegúrate de tener todos)
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put; // Necesitarás 'put' y 'delete' si los implementas en el futuro
import static spark.Spark.delete;


/**
 * ItemController
 * Handles all HTTP routes related to items, including API (JSON) and Web (HTML) views.
 */
public class ItemControllers {

    private final ItemService itemService = new ItemService();
    private final FilterService filterService = new FilterService();
    private final Gson gson = new Gson();
    // Instancia del motor de plantillas
    private final MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

    public ItemControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        // --- Rutas Web (HTML con Mustache) ---
        // ¡¡IMPORTANTE: Rutas específicas como '/new' y '/edit' van ANTES de las rutas con ':id'!!

        // Ruta para MOSTRAR el formulario de NUEVO item
        get("/items/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Añadir Artículo");
            model.put("item", null); // Pasamos null para indicar que es un formulario nuevo
            return templateEngine.render(
                new ModelAndView(model, "item-form.mustache")
            );
        });

        // Ruta para PROCESAR el formulario de NUEVO item (POST)
        post("/items/new", (req, res) -> {
            String name = req.queryParams("name");
            String category = req.queryParams("category");
            String priceStr = req.queryParams("price");
            String availableStr = req.queryParams("available");
            Map<String, Object> model = new HashMap<>();
            String error = null;
            double price = 0.0;
            boolean available = false;
            try {
                if (name == null || name.trim().isEmpty())
                    throw new IllegalArgumentException("El nombre del artículo es obligatorio");
                if (category == null || category.trim().isEmpty())
                    throw new IllegalArgumentException("La categoría es obligatoria");
                if (priceStr == null || priceStr.trim().isEmpty())
                    throw new IllegalArgumentException("El precio es obligatorio");
                price = Double.parseDouble(priceStr);
                if (price < 0)
                    throw new IllegalArgumentException("El precio debe ser positivo");
                available = "true".equalsIgnoreCase(availableStr);
                Item newItem = new Item(name, category, price, available);
                itemService.addItem(newItem);
                res.redirect("/items-web?message=creado");
                return null;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            model.put("title", "Añadir Artículo");
            model.put("item", new Item(name, category, price, available));
            model.put("error", error);
            return templateEngine.render(
                new ModelAndView(model, "item-form.mustache")
            );
        });

        // Ruta para MOSTRAR la lista de items en HTML
        get("/items-web", (req, res) -> {
            List<Item> allItems = itemService.getAllItems();
            Map<String, Object> model = new HashMap<>();
            model.put("items", allItems);
            model.put("title", "Lista de Artículos");
            String message = req.queryParams("message");
            if ("creado".equals(message)) {
                model.put("message", "¡Artículo creado exitosamente!");
            } else if ("editado".equals(message)) {
                model.put("message", "¡Artículo editado exitosamente!");
            }
            return templateEngine.render(
                new ModelAndView(model, "items.mustache")
            );
        });

        // Ruta para MOSTRAR el formulario de EDITAR item
        // Nota: Esta ruta también es específica y podría ir antes de /items-web/:id si hubiera conflicto
        get("/items-web/:id/edit", (req, res) -> {
            String id = req.params(":id");
            Item item = itemService.getItemById(id);
            Map<String, Object> model = new HashMap<>();

            if (item != null) {
                model.put("item", item);
                model.put("title", "Editar Artículo");
            } else {
                 res.redirect("/items-web");
                 return null;
            }
            return templateEngine.render(
                new ModelAndView(model, "item-form.mustache")
            );
        });

        // Ruta para PROCESAR el formulario de EDITAR item (POST)
        post("/items-web/:id/edit", (req, res) -> {
            String id = req.params(":id");
            String name = req.queryParams("name");
            String category = req.queryParams("category");
            String priceStr = req.queryParams("price");
            String availableStr = req.queryParams("available");
            Map<String, Object> model = new HashMap<>();
            String error = null;
            double price = 0.0;
            boolean available = false;
            try {
                if (name == null || name.trim().isEmpty())
                    throw new IllegalArgumentException("El nombre del artículo es obligatorio");
                if (category == null || category.trim().isEmpty())
                    throw new IllegalArgumentException("La categoría es obligatoria");
                if (priceStr == null || priceStr.trim().isEmpty())
                    throw new IllegalArgumentException("El precio es obligatorio");
                price = Double.parseDouble(priceStr);
                if (price < 0)
                    throw new IllegalArgumentException("El precio debe ser positivo");
                available = "true".equalsIgnoreCase(availableStr);
                Item updatedItemData = new Item(name, category, price, available);
                itemService.updateItem(id, updatedItemData);
                res.redirect("/items-web?message=editado");
                return null;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            model.put("title", "Editar Artículo");
            model.put("item", new Item(name, category, price, available));
            model.put("error", error);
            return templateEngine.render(
                new ModelAndView(model, "item-form.mustache")
            );
        });

         // Ruta para MOSTRAR el detalle de un item en HTML
         // ¡¡Esta ruta con parámetro va DESPUÉS de las rutas web específicas!!
        get("/items-web/:id", (req, res) -> {
            String id = req.params(":id");
            Item item = itemService.getItemById(id);
            Map<String, Object> model = new HashMap<>();

            if (item != null) {
                model.put("item", item);
                model.put("title", item.getName());
            } else {
                model.put("item", null);
                model.put("title", "Artículo No Encontrado");
                // Podríamos usar halt(404) para mostrar la página de error genérica
                // halt(404);
            }
            return templateEngine.render(
                new ModelAndView(model, "item-detail.mustache")
            );
        });


        // --- Rutas de API (JSON) ---
        // Estas pueden ir después, ya que usan '/items' y '/items/:id' que son distintos a '/items-web' etc.

        // Get all items API (con filtros)
        get("/items", (req, res) -> {
            res.type("application/json");
            String category = req.queryParams("category");
            String minPriceStr = req.queryParams("minprice");
            String maxPriceStr = req.queryParams("maxprice");
            Double minPrice = null;
            if (minPriceStr != null && !minPriceStr.isEmpty()) { try { minPrice = Double.parseDouble(minPriceStr); } catch (NumberFormatException e) { /* Ignorar o manejar */ } }
            Double maxPrice = null;
            if (maxPriceStr != null && !maxPriceStr.isEmpty()) { try { maxPrice = Double.parseDouble(maxPriceStr); } catch (NumberFormatException e) { /* Ignorar o manejar */ } }
            List<Item> allItems = itemService.getAllItems();
            List<Item> filteredItems = filterService.applyFilters(allItems, category, minPrice, maxPrice, null);
            return gson.toJson(filteredItems);
        });

        // Get an item by ID API
        // ¡¡Esta ruta API con parámetro va DESPUÉS de la ruta específica '/items/new' (si existiera para API)!!
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

        // Add a new item API
        post("/items", (req, res) -> {
            res.type("application/json");
            Item newItem = gson.fromJson(req.body(), Item.class);
            Item createdItem = itemService.addItem(newItem);
            res.status(201);
            return gson.toJson(createdItem);
        });

        // Aquí podrías añadir las rutas PUT y DELETE para la API si las necesitas
        // put("/items/:id", ... );
        // delete("/items/:id", ... );

    }

    // Clase interna para mensajes de respuesta JSON
    private static class ResponseMessage {
        String message;
        public ResponseMessage(String msg) { this.message = msg; }
    }
}