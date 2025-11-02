package org.johan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.johan.models.Item;

public class ItemService {

    private List<Item> items = new ArrayList<>();
    
    // --- 1. CONECTAMOS EL NUEVO SERVICIO DE FILTROS ---
    private final FilterService filterService;

    public ItemService() {
        // Datos de prueba
        items.add(new Item(UUID.randomUUID().toString(), "Funko Pop Batman", 15.99, "Juguetes"));
        items.add(new Item(UUID.randomUUID().toString(), "Comic Spider-Man #1", 45.50, "Comics"));
        items.add(new Item(UUID.randomUUID().toString(), "Sable de Luz", 250.00, "Juguetes"));
        
        // Inicializamos el servicio de filtros
        this.filterService = new FilterService();
    }

    // --- 2. MÉTODO SIMPLIFICADO (C2) ---
    // Ahora delega la lógica de filtrado al FilterService
    public List<Item> getAllItems(String categoria, String precioMax) {
        
        Double max = null;
        try {
            if (precioMax != null && !precioMax.isEmpty()) {
                max = Double.parseDouble(precioMax);
            }
        } catch (NumberFormatException e) {
            max = null; // Ignora el filtro si es inválido
        }
        
        // ¡Delegamos toda la lógica al servicio especializado!
        // (Asumimos que FilterService no maneja "disponible" o "minPrice" por ahora)
        return filterService.applyFilters(this.items, categoria, null, max, null);
    }

    public Item createItem(Item item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID().toString());
        }
        items.add(item);
        System.out.println("Item creado: " + item.getName());
        return item;
    }
}