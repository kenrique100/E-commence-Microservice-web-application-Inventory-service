package com.akentech.microservices.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) representing an inventory item.
 * Used to return inventory item details from the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponse {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
