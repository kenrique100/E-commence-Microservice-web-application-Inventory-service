package com.akentech.microservices.inventory.service.impl;

import com.akentech.microservices.inventory.dto.InventoryItemResponse;
import com.akentech.microservices.inventory.dto.InventoryRequest;
import com.akentech.microservices.inventory.dto.InventoryResponse;
import com.akentech.microservices.inventory.exception.InvalidQuantityException;
import com.akentech.microservices.inventory.exception.ResourceNotFoundException;
import com.akentech.microservices.inventory.model.Inventory;
import com.akentech.microservices.inventory.repository.InventoryRepository;
import com.akentech.microservices.inventory.service.InventoryService;
import com.akentech.microservices.inventory.util.InventoryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryService.
 * Provides methods to check inventory availability and retrieve inventory details.
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Checks if the requested quantity of a product is available in stock.
     * Throws an exception if the quantity is invalid.
     *
     * @param inventoryRequest Request containing SKU code and quantity.
     * @return InventoryResponse indicating if the item is in stock.
     */
    @Override
    public InventoryResponse checkInventory(InventoryRequest inventoryRequest) {
        // Validate quantity before proceeding
        if (!InventoryUtil.isQuantityValid(inventoryRequest.getQuantity())) {
            throw new InvalidQuantityException("Invalid quantity: Quantity must be non-null and greater than or equal to 0.");
        }

        // Check if stock is available
        boolean isInStock = inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(
                inventoryRequest.getSkuCode(), inventoryRequest.getQuantity());

        return new InventoryResponse(inventoryRequest.getSkuCode(), isInStock);
    }

    /**
     * Retrieves a list of all inventory items.
     *
     * @return List of InventoryItemResponse objects.
     */
    @Override
    public List<InventoryItemResponse> getAllInventoryItems() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToInventoryItemResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an inventory item by its unique ID.
     * Throws an exception if the item is not found.
     *
     * @param id The ID of the inventory item.
     * @return InventoryItemResponse containing inventory details.
     */
    @Override
    public InventoryItemResponse getInventoryItemById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + id));
        return mapToInventoryItemResponse(inventory);
    }

    /**
     * Retrieves an inventory item using its SKU code.
     * Throws an exception if the item is not found.
     *
     * @param skuCode The SKU code of the product.
     * @return InventoryItemResponse containing inventory details.
     */
    @Override
    public InventoryItemResponse getInventoryItemBySkuCode(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with SKU code: " + skuCode));
        return mapToInventoryItemResponse(inventory);
    }

    /**
     * Maps an Inventory entity to an InventoryItemResponse DTO.
     *
     * @param inventory The Inventory entity.
     * @return InventoryItemResponse containing inventory details.
     */
    private InventoryItemResponse mapToInventoryItemResponse(Inventory inventory) {
        return new InventoryItemResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }
}
