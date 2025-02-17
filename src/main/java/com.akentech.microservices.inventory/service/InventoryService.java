package com.akentech.microservices.inventory.service;

import com.akentech.microservices.common.dto.InventoryRequest;
import com.akentech.microservices.common.dto.InventoryResponse;
import com.akentech.microservices.inventory.dto.InventoryItemResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse checkInventory(InventoryRequest inventoryRequest);
    List<InventoryItemResponse> getAllInventoryItems();
    InventoryItemResponse getInventoryItemById(Long id);
    InventoryItemResponse getInventoryItemBySkuCode(String skuCode);
    void deleteInventoryItem(Long id);
}