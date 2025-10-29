package org.johan.services;

import org.johan.models.Item;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FilterService
 *
 * Provides utility methods to apply filters on items based on
 * category, price range, and availability.
 *
 * This service can be used by ItemController or ItemService to
 * respond to user queries with specific filtering options.
 */
public class FilterService {

    /**
     * Applies category and price filters to a list of items.
     *
     * @param items - List of all available items
     * @param category - Category filter (can be null)
     * @param minPrice - Minimum price filter (can be null)
     * @param maxPrice - Maximum price filter (can be null)
     * @param available - Availability filter (can be null)
     * @return Filtered list of items that match the criteria
     */
    public List<Item> applyFilters(List<Item> items, String category, Double minPrice, Double maxPrice, Boolean available) {

        return items.stream()
                .filter(i -> (category == null || i.getCategory().equalsIgnoreCase(category)))
                .filter(i -> (minPrice == null || i.getPrice() >= minPrice))
                .filter(i -> (maxPrice == null || i.getPrice() <= maxPrice))
                .filter(i -> (available == null || i.isAvailable() == available))
                .collect(Collectors.toList());
    }

}