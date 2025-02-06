package com.akentech.microservices.inventory.service;

import com.akentech.microservices.inventory.dto.InventoryItemResponse;
import com.akentech.microservices.inventory.dto.InventoryRequest;
import com.akentech.microservices.inventory.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse checkInventory(InventoryRequest inventoryRequest);
    List<InventoryItemResponse> getAllInventoryItems();
    InventoryItemResponse getInventoryItemById(Long id);
    InventoryItemResponse getInventoryItemBySkuCode(String skuCode);
}