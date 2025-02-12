package com.akentech.microservices.inventory.controller;

import com.akentech.microservices.inventory.dto.InventoryItemResponse;
import com.akentech.microservices.inventory.dto.InventoryRequest;
import com.akentech.microservices.inventory.dto.InventoryResponse;
import com.akentech.microservices.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling inventory-related API requests.
 * Exposes endpoints to check inventory, fetch all inventory items, and retrieve inventory by ID or SKU code.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor // Generates a constructor for final fields (injects InventoryService)
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Checks the availability of a product in inventory.
     *
     * @param inventoryRequest The request containing SKU code and quantity.
     * @return InventoryResponse containing SKU code and stock availability.
     */
    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse checkInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.checkInventory(inventoryRequest);
    }

    /**
     * Retrieves a list of all inventory items.
     *
     * @return A list of InventoryItemResponse objects.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryItemResponse> getAllInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    /**
     * Retrieves inventory item details by its unique ID.
     *
     * @param id The ID of the inventory item.
     * @return InventoryItemResponse object containing inventory details.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryItemResponse getInventoryItemById(@PathVariable Long id) {
        return inventoryService.getInventoryItemById(id);
    }

    /**
     * Retrieves inventory item details using SKU code.
     *
     * @param skuCode The SKU code of the product.
     * @return InventoryItemResponse containing inventory details.
     */
    @GetMapping("/sku/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryItemResponse getInventoryItemBySkuCode(@PathVariable String skuCode) {
        return inventoryService.getInventoryItemBySkuCode(skuCode);
    }
}
