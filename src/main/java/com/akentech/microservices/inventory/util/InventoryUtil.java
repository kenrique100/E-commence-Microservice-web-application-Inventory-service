package com.akentech.microservices.inventory.util;

/**
 * Utility class for inventory-related operations.
 */
public class InventoryUtil {

    /**
     * Validates that the given quantity is non-null and greater than or equal to zero.
     *
     * @param quantity The quantity to validate.
     * @return true if the quantity is valid, false otherwise.
     */
    public static boolean isQuantityValid(Integer quantity) {
        return quantity != null && quantity >= 0;
    }
}
