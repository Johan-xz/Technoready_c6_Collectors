package org.johan.controllers;

import java.util.HashMap;
import java.util.Map;

import org.johan.models.Item;
import org.johan.services.ItemService;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

public class ItemController {

    private final ItemService itemService;
    private final MustacheTemplateEngine mustacheEngine;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
        this.mustacheEngine = new MustacheTemplateEngine();
    }

    /**
     * Tarea 2: Renderiza la vista de la tienda con los items
     * Sprint 3: Filtros mejorados (categoría, precio mínimo, precio máximo, disponibilidad)
     */
    public String renderTienda(Request req, Response res) {
        // 1. Leemos los parámetros de la URL (query params)
        String categoria = req.queryParams("categoria");
        String precioMin = req.queryParams("precioMin");
        String precioMax = req.queryParams("precioMax");
        String disponible = req.queryParams("disponible");

        // 2. Prepara los datos
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", "Tienda");
        model.put("username", "Ramón");
        model.put("activeTienda", true);
        
        // 3. Pasamos los filtros al servicio
        var items = itemService.getAllItems(categoria, precioMin, precioMax, disponible);
        model.put("items", items);
        model.put("hasItems", items != null && !items.isEmpty());
        
        // 4. Guardamos los valores de filtro para mostrarlos en el form
        model.put("filtroCategoria", categoria != null ? categoria : "");
        model.put("filtroPrecioMin", precioMin != null ? precioMin : "");
        model.put("filtroPrecioMax", precioMax != null ? precioMax : "");
        
        // Manejo especial para el filtro disponible (para el template Mustache)
        if (disponible != null && !disponible.isEmpty()) {
            Map<String, Object> disponibleMap = new HashMap<>();
            disponibleMap.put("value", disponible);
            disponibleMap.put("isTrue", "true".equals(disponible));
            disponibleMap.put("isFalse", "false".equals(disponible));
            model.put("filtroDisponible", disponibleMap);
        }

        return mustacheEngine.render(
            new ModelAndView(model, "tienda.mustache")
        );
    }

    /**
     * Tarea 3: Maneja el envío del formulario web
     */
    public String createItem(Request req, Response res) {
        // 1. Obtiene los datos del formulario
        String name = req.queryParams("itemName");
        String category = req.queryParams("itemCategory");
        double price = 0;
        
        try {
            price = Double.parseDouble(req.queryParams("itemPrice"));
        } catch (NumberFormatException e) {
            // (Aquí podrías lanzar una ValidationException si quisieras)
            price = 0.0; // Valor por defecto si el precio es inválido
        }

        // 2. Crea el item
        Item newItem = new Item(null, name, price, category, "");
        itemService.createItem(newItem);

        // 3. Redirige al usuario de vuelta a la tienda
        res.redirect("/tienda");
        return null;
    }
}