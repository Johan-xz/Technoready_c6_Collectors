package org.johan.controllers;

import java.util.HashMap;
import java.util.List;

import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;
import org.johan.models.Offer;
import org.johan.services.OfferService;

import com.google.gson.Gson;

import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.mustache.MustacheTemplateEngine;

/**
 * OfferController
 * Defines routes for handling item offers.
 * Supports retrieving all offers and a specific offer by ID.
 */
public class OffersControllers {

    private final OfferService offerService = new OfferService();
    private final Gson gson = new Gson();

    public OffersControllers() {
        System.out.println("[OffersControllers] Inicializando y registrando rutas...");
        defineRoutes();
        System.out.println("[OffersControllers] Rutas registradas: /offers, /offers/:id, /offers-web, /offers/new");
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
                throw new NotFoundException("Offer not found");
            }
            return gson.toJson(offer);
        });

        // POST /offers -> create a new bid/auction entry
        post("/offers", (req, res) -> {
            res.type("application/json");
            Offer newOffer = gson.fromJson(req.body(), Offer.class);
            // Validación básica para subastas
            if (newOffer == null || newOffer.getItemId() == null || newOffer.getItemId().trim().isEmpty()) {
                throw new ValidationException("El ID de item es obligatorio en una puja");
            }
            if (newOffer.getCurrentBid() <= 0) {
                throw new ValidationException("La puja debe ser mayor que 0");
            }
            Offer createdOffer = offerService.addOffer(newOffer);
            res.status(201);
            return gson.toJson(createdOffer);
        });

        // Vistas WEB
        get("/offers-web", (req, res) -> {
            System.out.println("[OffersControllers] GET /offers-web");
            List<Offer> offers = offerService.getAllOffers();
            HashMap<String, Object> model = new HashMap<>();
            model.put("pageTitle", "Ofertas");
            model.put("activeOffers", true);
            model.put("offers", offers);
            String message = req.queryParams("message");
            if ("oferta-creada".equals(message)) {
                model.put("message", "¡Oferta creada exitosamente!");
            }
            return new MustacheTemplateEngine()
                .render(new ModelAndView(model, "offers.mustache"));
        });
        // Soportar trailing slash
        get("/offers-web/", (req, res) -> {
            res.redirect("/offers-web");
            return null;
        });
        get("/offers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("pageTitle", "Crear Oferta");
            model.put("activeOffers", true);
            return new MustacheTemplateEngine()
                .render(new ModelAndView(model, "offer-form.mustache"));
        });
        post("/offers/new", (req, res) -> {
            String itemId = req.queryParams("itemId");
            String bidStr = req.queryParams("bidAmount");
            String bidder = req.queryParams("bidder");
            HashMap<String, Object> model = new HashMap<>();
            double bid = 0.0;
            String error = null;
            try {
                if (itemId == null || itemId.trim().isEmpty()) {
                    throw new ValidationException("El ID de item es obligatorio");
                }
                if (bidStr == null || bidStr.trim().isEmpty()) {
                    throw new ValidationException("La puja es obligatoria");
                }
                bid = Double.parseDouble(bidStr);
                if (bid <= 0) {
                    throw new ValidationException("La puja debe ser mayor a 0");
                }
                // Crear y guardar puja
                Offer offer = new Offer(itemId, bid, bidder);
                offerService.addOffer(offer);
                res.redirect("/offers-web?message=oferta-creada");
                return null;
            } catch (ValidationException | NumberFormatException ve) {
                error = ve.getMessage();
            }
            model.put("pageTitle", "Crear Oferta");
            model.put("activeOffers", true);
            model.put("error", error);
            model.put("offer", new Offer(itemId, bid, bidder));
            return new MustacheTemplateEngine().render(new ModelAndView(model, "offer-form.mustache"));
        });
        // Ruta de diagnóstico rápida para validar el registro del controlador
        get("/offers-web-debug", (req, res) -> {
            res.type("text/plain");
            return "OK: offers web controller reachable";
        });
    }
}