package org.johan.models;

import java.util.UUID;

/**
 * Represents an item that can be sold or offered in the platform.
 */
public class Item {

    private String id;
    private String name;
    private String category;
    private double price;
    private boolean available;

    public Item() {
        this.id = UUID.randomUUID().toString();
    }

    public Item(String name, String category, double price, boolean available) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}