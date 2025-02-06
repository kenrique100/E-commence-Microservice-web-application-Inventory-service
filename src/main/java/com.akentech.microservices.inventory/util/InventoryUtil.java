package com.akentech.microservices.inventory.util;

public class InventoryUtil {

    public static boolean isQuantityValid(Integer quantity) {
        return quantity != null && quantity >= 0;
    }
}