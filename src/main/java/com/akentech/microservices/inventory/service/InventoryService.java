package com.akentech.microservices.inventory.service;

import com.akentech.microservices.inventory.dto.InventoryItemResponse;
import com.akentech.microservices.inventory.dto.InventoryRequest;
import com.akentech.microservices.inventory.dto.InventoryResponse;

import java.util.List;

/**
 * Service interface defining inventory operations.
 */
public interface InventoryService {

    /**
     * Checks if the requested quantity of an item is available in stock.
     *
     * @param inventoryRequest Request containing SKU code and quantity.
     * @return InventoryResponse indicating stock availability.
     */
    InventoryResponse checkInventory(InventoryRequest inventoryRequest);

    /**
     * Retrieves all inventory items.
     *
     * @return List of InventoryItemResponse objects.
     */
    List<InventoryItemResponse> getAllInventoryItems();

    /**
     * Retrieves an inventory item by its ID.
     *
     * @param id The ID of the inventory item.
     * @return InventoryItemResponse containing inventory details.
     */
    InventoryItemResponse getInventoryItemById(Long id);

    /**
     * Retrieves an inventory item by its SKU code.
     *
     * @param skuCode The SKU code of the product.
     * @return InventoryItemResponse containing inventory details.
     */
    InventoryItemResponse getInventoryItemBySkuCode(String skuCode);
}
