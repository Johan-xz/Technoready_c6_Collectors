package org.johan.services;

import org.johan.models.Item;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class that handles all business logic related to items.
 * Acts as an in-memory store for demo and API development.
 */
public class ItemService {

    private final List<Item> items = new ArrayList<>();

    public ItemService() {
        // preload some demo items
        items.add(new Item("Vintage Comic Book", "collectibles", 50.0, true));
        items.add(new Item("Retro Action Figure", "collectibles", 80.0, false));
        items.add(new Item("Classic Trading Card", "cards", 20.0, true));
        items.add(new Item("Limited Edition Poster", "decor", 35.0, true));
    }

    /** Retrieves all items in the catalog **/
    public List<Item> getAllItems() {
        return items;
    }

    /** Retrieves an item by its unique ID **/
    public Item getItemById(String id) {
        return items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /** Adds a new item to the catalog **/
    public Item addItem(Item newItem) {
        items.add(newItem);
        return newItem;
    }

    /** Applies filter criteria (category and price range) **/
    public List<Item> filterItems(String category, Double min, Double max) {
        return items.stream()
                .filter(i -> (category == null || i.getCategory().equalsIgnoreCase(category)))
                .filter(i -> (min == null || i.getPrice() >= min))
                .filter(i -> (max == null || i.getPrice() <= max))
                .collect(Collectors.toList());
    }

    /** Removes an item by id **/
    public boolean deleteItemById(String id) {
        return items.removeIf(i -> i.getId().equals(id));
    }

    /** Updates an existing item **/
    public Item updateItem(String id, Item updatedItem) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                item.setName(updatedItem.getName());
                item.setCategory(updatedItem.getCategory());
                item.setPrice(updatedItem.getPrice());
                item.setAvailable(updatedItem.isAvailable());
                return item;
            }
        }
        return null;
    }
}