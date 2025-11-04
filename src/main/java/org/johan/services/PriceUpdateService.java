package org.johan.services;

import org.johan.models.Item;
import org.johan.websocket.PriceWebSocket;

/**
 * Servicio para manejar las actualizaciones de precios en tiempo real
 */
public class PriceUpdateService {
    private final ItemService itemService;

    public PriceUpdateService(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Actualiza el precio de un item y notifica a todos los clientes conectados
     * 
     * @param itemId   ID del item a actualizar
     * @param newPrice Nuevo precio
     * @return El item actualizado
     */
    public Item updatePrice(String itemId, double newPrice) {
        // Validar que el precio sea positivo
        if (newPrice <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }

        // Actualizar el precio en la base de datos
        Item updatedItem = itemService.updateItemPrice(itemId, newPrice);

        // Notificar a todos los clientes conectados
        String message = String.format("%s:%.2f", itemId, newPrice);
        PriceWebSocket.broadcast(message);

        return updatedItem;
    }
}
