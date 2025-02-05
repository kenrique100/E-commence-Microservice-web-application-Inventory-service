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

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse checkInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.checkInventory(inventoryRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryItemResponse> getAllInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryItemResponse getInventoryItemById(@PathVariable Long id) {
        return inventoryService.getInventoryItemById(id);
    }

    @GetMapping("/sku/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryItemResponse getInventoryItemBySkuCode(@PathVariable String skuCode) {
        return inventoryService.getInventoryItemBySkuCode(skuCode);
    }
}