package org.johan.models;

import java.util.UUID;

/**
 * Represents a promotional offer associated with an item.
 */
public class Offer {

    private String id;
    private String itemId;
    private double discountPercentage;
    private String validUntil;

    public Offer() {
        this.id = UUID.randomUUID().toString();
    }

    public Offer(String itemId, double discountPercentage, String validUntil) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.discountPercentage = discountPercentage;
        this.validUntil = validUntil;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
}