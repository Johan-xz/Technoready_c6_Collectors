package org.johan.services;

import org.johan.models.Offer;

import java.util.*;

/**
 * Service for managing item offers.
 * Holds offer data in memory for demonstration and testing.
 */
public class OfferService {

    private final List<Offer> offers = new ArrayList<>();

    public OfferService() {
        // preload some sample offers
        offers.add(new Offer("1", 10.0, "2025-12-31"));
        offers.add(new Offer("2", 25.0, "2025-10-15"));
        offers.add(new Offer("3", 15.0, "2025-09-30"));
    }

    /** Retrieve all available offers **/
    public List<Offer> getAllOffers() {
        return offers;
    }

    /** Retrieve a specific offer by ID **/
    public Offer getOfferById(String id) {
        return offers.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /** Add a new offer **/
    public Offer addOffer(Offer newOffer) {
        offers.add(newOffer);
        return newOffer;
    }

    /** Delete an offer by its ID **/
    public boolean deleteOfferById(String id) {
        return offers.removeIf(o -> o.getId().equals(id));
    }

    /** Update an existing offer **/
    public Offer updateOffer(String id, Offer updatedOffer) {
        for (Offer offer : offers) {
            if (offer.getId().equals(id)) {
                offer.setItemId(updatedOffer.getItemId());
                offer.setDiscountPercentage(updatedOffer.getDiscountPercentage());
                offer.setValidUntil(updatedOffer.getValidUntil());
                return offer;
            }
        }
        return null;
    }
}