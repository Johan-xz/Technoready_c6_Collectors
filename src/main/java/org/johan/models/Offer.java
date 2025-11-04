package org.johan.models;

import java.util.UUID;

/**
 * Represents an auction bid associated with an item.
 */
public class Offer {

    private String id;
    private String itemId;
    private double currentBid; 
    private String bidder;     

    public Offer() {
        this.id = UUID.randomUUID().toString();
    }

    public Offer(String itemId, double currentBid, String bidder) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.currentBid = currentBid;
        this.bidder = bidder;
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

    public double getCurrentBid() { return currentBid; }
    public void setCurrentBid(double currentBid) { this.currentBid = currentBid; }
    public String getBidder() { return bidder; }
    public void setBidder(String bidder) { this.bidder = bidder; }
}