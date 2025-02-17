package com.akentech.microservices.inventory.service.impl;

import com.akentech.microservices.common.dto.InventoryRequest;
import com.akentech.microservices.common.dto.InventoryResponse;
import com.akentech.microservices.inventory.dto.InventoryItemResponse;
import com.akentech.microservices.inventory.exception.ResourceNotFoundException;
import com.akentech.microservices.inventory.model.Inventory;
import com.akentech.microservices.inventory.repository.InventoryRepository;
import com.akentech.microservices.inventory.service.InventoryService;
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

    @Override
    public void deleteInventoryItem(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory item not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }

    private InventoryItemResponse mapToInventoryItemResponse(Inventory inventory) {
        return new InventoryItemResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }
}