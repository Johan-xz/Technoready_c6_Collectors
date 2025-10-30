package org.johan.models;

public class Item {
    private String id;
    private String name;
    private double price;
    private String category;
    
    // --- CAMBIO AQUÍ ---
    // Añadimos un campo 'available' para que el FilterService funcione
    private boolean available; 

    public Item(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = true; // Hacemos que todos los items nuevos estén disponibles
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    
    // --- CAMBIO AQUÍ ---
    // Añadimos el getter que faltaba
    public boolean isAvailable() { return available; } 
    
    public void setId(String id) { this.id = id; }
    public void setAvailable(boolean available) { this.available = available; }
}