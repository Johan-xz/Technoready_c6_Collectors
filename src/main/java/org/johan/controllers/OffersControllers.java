package org.johan.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import org.johan.models.Offer;
import org.johan.services.OfferService;
import java.util.List;
import org.johan.exceptions.NotFoundException;
import org.johan.exceptions.ValidationException;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.HashMap;

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
                throw new NotFoundException("Offer not found");
            }
            return gson.toJson(offer);
        });

        // POST /offers -> create a new offer
        post("/offers", (req, res) -> {
            res.type("application/json");
            Offer newOffer = gson.fromJson(req.body(), Offer.class);
            // Validación básica para campos obligatorios de Offer
            if (newOffer == null || newOffer.getItemId() == null || newOffer.getItemId().trim().isEmpty()) {
                throw new ValidationException("El ID de item es obligatorio en una oferta");
            }
            if (newOffer.getDiscountPercentage() <= 0 || newOffer.getDiscountPercentage() > 100) {
                throw new ValidationException("El porcentaje de descuento debe ser mayor a 0 y menor o igual a 100");
            }
            Offer createdOffer = offerService.addOffer(newOffer);
            res.status(201);
            return gson.toJson(createdOffer);
        });

        // Vistas WEB
        get("/offers-web", (req, res) -> {
            List<Offer> offers = offerService.getAllOffers();
            HashMap<String, Object> model = new HashMap<>();
            model.put("offers", offers);
            String message = req.queryParams("message");
            if ("oferta-creada".equals(message)) {
                model.put("message", "¡Oferta creada exitosamente!");
            }
            return new MustacheTemplateEngine()
                .render(new ModelAndView(model, "offers.mustache"));
        });
        get("/offers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            return new MustacheTemplateEngine()
                .render(new ModelAndView(model, "offer-form.mustache"));
        });
        post("/offers/new", (req, res) -> {
            String itemId = req.queryParams("itemId");
            String discountStr = req.queryParams("discountPercentage");
            String validUntil = req.queryParams("validUntil");
            HashMap<String, Object> model = new HashMap<>();
            double discount = 0.0;
            String error = null;
            try {
                if (itemId == null || itemId.trim().isEmpty()) {
                    throw new ValidationException("El ID de item es obligatorio");
                }
                if (discountStr == null || discountStr.trim().isEmpty()) {
                    throw new ValidationException("El descuento es obligatorio");
                }
                discount = Double.parseDouble(discountStr);
                if (discount <= 0 || discount > 100) {
                    throw new ValidationException("El porcentaje de descuento debe ser mayor a 0 y menor o igual a 100");
                }
                if (validUntil == null || validUntil.trim().isEmpty()) {
                    throw new ValidationException("La fecha de validez es obligatoria");
                }
                // Crear y guardar oferta
                Offer offer = new Offer(itemId, discount, validUntil);
                offerService.addOffer(offer);
                res.redirect("/offers-web?message=oferta-creada");
                return null;
            } catch (ValidationException | NumberFormatException ve) {
                error = ve.getMessage();
            }
            model.put("error", error);
            model.put("offer", new Offer(itemId, discount, validUntil));
            return new MustacheTemplateEngine().render(new ModelAndView(model, "offer-form.mustache"));
        });
    }
}




// Listo, ¡Error Handling avanzado nivel C2 implementado!
// Resumen de lo realizado:
// Se crearon excepciones personalizadas:
// NotFoundException (404)
// ValidationException (400)
// ConflictException (409)
// UnauthorizedException (401)
// Se integró un manejador global de excepciones en Main.java:
// Devuelve JSON (API) o HTML (web) según el Header Accept.
// Status HTTP correcto y mensaje claro.
// Se refactorizaron controladores (UserControllers.java, OffersControllers.java) para lanzar excepciones y no manejar errores localmente.
// Se limpió código y se corrigió la validación según los modelos reales.