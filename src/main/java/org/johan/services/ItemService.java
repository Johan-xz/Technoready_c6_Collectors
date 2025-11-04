package org.johan.services;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.johan.models.Item;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Service that manages a simple in-memory collection of {@link Item} objects.
 * <p>
 * This class provides a minimal set of operations useful for the sample web
 * application: listing items with optional filters (delegated to
 * {@link FilterService}), creating new items and updating an item's price.
 * </p>
 * <p>
 * Note: Persistence is not implemented; items are held in-memory in a
 * {@code List<Item>} and initialized with a small sample dataset.
 * </p>
 */
public class ItemService {

    /** In-memory list that stores the application's items. */
    private List<Item> items = new ArrayList<>();

    // 1. CONECTAMOS EL NUEVO SERVICIO DE FILTROS
    /**
     * Dedicated service that encapsulates filter logic. Use this to keep the
     * ItemService focused on data storage and simple operations.
     */
    private final FilterService filterService;

    /**
     * Construct an ItemService and populate the in-memory list with sample
     * items. Also initializes the {@link FilterService} used by
     * {@link #getAllItems(String, String, String, String)}.
     */
    public ItemService() {
        // Initialize the filter service
        this.filterService = new FilterService();

        // Attempt to load items from classpath resource data/items.json. If the
        // resource is missing or parsing fails we fall back to an internal
        // sample dataset (so the application remains functional).
        boolean loaded = loadItemsFromJson();
        if (!loaded) {
            // Sample dataset used by the demo application (fallback)
            items.add(new Item("item1", "Gorra autografiada por Peso Pluma", parsePrice("$621.34 USD"), "Memorabilia",
                    "Gorra.jpeg"));
            items.add(new Item("item2", "Casco autografiado por Rosalía", parsePrice("$734.57 USD"), "Memorabilia",
                    "casco.png"));
            items.add(new Item("item3", "Chamarra de Bad Bunny", parsePrice("$521.89 USD"), "Memorabilia",
                    "chamarra.png"));
            items.add(new Item("item4", "Guitarra de Fernando Delgadillo", parsePrice("$823.12 USD"), "Instrumentos",
                    "guitarra.png"));
            items.add(new Item("item5", "Jersey firmado por Snoop Dogg", parsePrice("$355.67 USD"), "Ropa",
                    "jersey.png"));
            items.add(new Item("item6", "Prenda de Cardi B autografiada", parsePrice("$674.23 USD"), "Ropa",
                    "prenda.png"));
            items.add(new Item("item7", "Guitarra autografiada por Coldplay", parsePrice("$458.91 USD"), "Instrumentos",
                    "coldplay.png"));
        }
    }

    /**
     * Load initial items from the classpath JSON file `data/items.json` using
     * Gson. The JSON is expected to be an array of objects containing at least
     * the fields: id, name and price (price as a string such as "$621.34 USD").
     * Optional fields are description, category, imageUrl and available.
     *
     * @return true when items were successfully loaded (and added to
     *         {@link #items}), false otherwise.
     */
    private boolean loadItemsFromJson() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("data/items.json");
        if (is == null) {
            return false;
        }

        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            String json = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            if (json.isEmpty()) {
                return false;
            }

            Gson gson = new Gson();
            Type listType = new TypeToken<List<ItemJson>>() {
            }.getType();
            List<ItemJson> list = gson.fromJson(json, listType);
            if (list == null || list.isEmpty()) {
                return false;
            }

            for (ItemJson ij : list) {
                String id = ij.id != null ? ij.id : UUID.randomUUID().toString();
                String name = ij.name != null ? ij.name : "";
                double price = parsePrice(ij.price);
                String category = ij.category != null ? ij.category : "Uncategorized";
                String imageUrl = ij.imageUrl != null ? ij.imageUrl : null;
                Item item = new Item(id, name, price, category, imageUrl);
                if (ij.available != null) {
                    item.setAvailable(ij.available);
                }
                items.add(item);
            }

            return true;
        } catch (Exception e) {
            // Parsing failed; return false so the caller can fallback to sample
            // data. Avoid propagating exceptions for the demo app.
            return false;
        }
    }

    /**
     * Small helper type used only to parse the incoming JSON structure.
     */
    private static class ItemJson {
        String id;
        String name;
        String description;
        String price;
        String category;
        String imageUrl;
        Boolean available;
    }

    /**
     * Helper method that converts a human readable price such as
     * "$621.34 USD" into a primitive double value 621.34.
     *
     * @param priceStr text representation of the price, may contain currency
     *                 symbols and text. If {@code null} or not parseable, the
     *                 method returns 0.0.
     * @return parsed numeric price as {@code double}, or 0.0 on invalid input
     */
    private double parsePrice(String priceStr) {
        if (priceStr == null)
            return 0.0;
        String cleaned = priceStr.replaceAll("[^\\d.]", "");
        if (cleaned.isEmpty())
            return 0.0;
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // 2. MÉTODO SIMPLIFICADO (C2)
    // Sprint 3: Soporte completo para todos los filtros (categoría, precio mínimo,
    // precio máximo, disponibilidad)
    // Ahora delega la lógica de filtrado al FilterService
    /**
     * Return all items, optionally filtered by category, minimum price,
     * maximum price and availability.
     * <p>
     * The incoming filter values are received as strings (to match typical
     * HTTP query parameters). This method attempts to parse the numeric and
     * boolean values; invalid numeric values are ignored (that filter is
     * skipped). The actual filter implementation is delegated to
     * {@link FilterService#applyFilters(List, String, Double, Double, Boolean)}.
     * </p>
     *
     * @param categoria  category name to filter by (nullable)
     * @param precioMin  minimum price as a String (nullable). If parseable it
     *                   will be converted to {@link Double} and applied.
     * @param precioMax  maximum price as a String (nullable). If parseable it
     *                   will be converted to {@link Double} and applied.
     * @param disponible availability flag as a String (nullable). Expected
     *                   values are "true" or "false" (case-insensitive).
     * @return list of items matching the provided filters (never {@code null})
     */
    public List<Item> getAllItems(String categoria, String precioMin, String precioMax, String disponible) {

        Double min = null;
        Double max = null;
        Boolean available = null;

        try {
            if (precioMin != null && !precioMin.isEmpty()) {
                min = Double.parseDouble(precioMin);
            }
            if (precioMax != null && !precioMax.isEmpty()) {
                max = Double.parseDouble(precioMax);
            }
            if (disponible != null && !disponible.isEmpty()) {
                available = Boolean.parseBoolean(disponible);
            }
        } catch (NumberFormatException e) {
            // Ignore invalid numeric filters: treat them as absent
        }

        // Delegate filtering to the dedicated service
        return filterService.applyFilters(this.items, categoria, min, max, available);
    }

    /**
     * Create and add a new item to the in-memory list. If the provided item has
     * no id, a new random UUID will be assigned.
     *
     * @param item item to create and store (must not be {@code null})
     * @return the stored item instance (same reference as the parameter but
     *         with id set when previously absent)
     */
    public Item createItem(Item item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID().toString());
        }
        items.add(item);
        System.out.println("Item creado: " + item.getName());
        return item;
    }

    /**
     * Update the numeric price of an existing item identified by its id.
     *
     * @param itemId   identifier of the item to update
     * @param newPrice new price value to set
     * @return the updated item instance
     * @throws IllegalArgumentException when no item with the given id exists
     */
    public Item updateItemPrice(String itemId, double newPrice) {
        Item item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: " + itemId));

        item.setPrice(newPrice);
        return item;
    }
}