package org.johan.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import org.johan.models.Offer;
import org.johan.services.OfferService;
import java.util.List;

/**
 * OfferController
 * Defines routes for handling item offers.
 * Supports retrieving all offers and a specific offer by ID.
 */
public class OffersControllers {

    private final OfferService offerService = new OfferService();
    private final Gson gson = new Gson();

    public OffersControllers() {
        defineRoutes();
    }

    private void defineRoutes() {

        // GET /offers -> list all current offers
        get("/offers", (req, res) -> {
            res.type("application/json");
            List<Offer> offers = offerService.getAllOffers();
            return gson.toJson(offers);
        });

        // GET /offers/:id -> retrieve a specific offer by ID
        get("/offers/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            Offer offer = offerService.getOfferById(id);

            if (offer == null) {
                res.status(404);
                return gson.toJson(new ResponseMessage("Offer not found"));
            }
            return gson.toJson(offer);
        });

        // POST /offers -> create a new offer
        post("/offers", (req, res) -> {
            res.type("application/json");
            Offer newOffer = gson.fromJson(req.body(), Offer.class);
            Offer createdOffer = offerService.addOffer(newOffer);
            res.status(201);
            return gson.toJson(createdOffer);
        });
    }

    // Helper message class for responses
    private static class ResponseMessage {
        String message;
        public ResponseMessage(String msg) { this.message = msg; }
    }
}