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
     */
    public String renderTienda(Request req, Response res) {
        // 1. Leemos los parámetros de la URL (query params)
        String categoria = req.queryParams("categoria");
        String precioMax = req.queryParams("precioMax");

        // 2. Prepara los datos
        Map<String, Object> model = new HashMap<>();
        model.put("username", "Ramón");
        
        // 3. Pasamos los filtros al servicio
        model.put("items", itemService.getAllItems(categoria, precioMax));
        
        // 4. Guardamos los valores de filtro para mostrarlos en el form
        model.put("filtroCategoria", categoria);
        model.put("filtroPrecio", precioMax);

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
        Item newItem = new Item(null, name, price, category);
        itemService.createItem(newItem);

        // 3. Redirige al usuario de vuelta a la tienda
        res.redirect("/tienda");
        return null;
    }
}