package com.akentech.microservices.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing an inventory check request.
 * Contains the SKU code and quantity to check availability.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {

    @NotBlank(message = "SKU Code is required")
    private String skuCode;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}
