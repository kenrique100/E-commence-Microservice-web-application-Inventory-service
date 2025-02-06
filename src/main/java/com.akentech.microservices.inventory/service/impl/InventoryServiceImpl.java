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

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse checkInventory(InventoryRequest inventoryRequest) {
        if (!InventoryUtil.isQuantityValid(inventoryRequest.getQuantity())) {
            throw new InvalidQuantityException("Invalid quantity: Quantity must be non-null and greater than or equal to 0.");
        }

        boolean isInStock = inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(
                inventoryRequest.getSkuCode(), inventoryRequest.getQuantity());

        return new InventoryResponse(inventoryRequest.getSkuCode(), isInStock);
    }

    @Override
    public List<InventoryItemResponse> getAllInventoryItems() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToInventoryItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryItemResponse getInventoryItemById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + id));
        return mapToInventoryItemResponse(inventory);
    }

    @Override
    public InventoryItemResponse getInventoryItemBySkuCode(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with SKU code: " + skuCode));
        return mapToInventoryItemResponse(inventory);
    }

    private InventoryItemResponse mapToInventoryItemResponse(Inventory inventory) {
        return new InventoryItemResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }
}