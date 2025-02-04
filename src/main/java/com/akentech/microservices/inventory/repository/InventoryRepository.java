package com.akentech.microservices.inventory.repository;

import com.akentech.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Inventory entity.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Checks if an inventory item with the given SKU code has at least the specified quantity.
     * Used to validate stock availability.
     *
     * @param skuCode The SKU code of the product.
     * @param quantity The requested quantity.
     * @return true if enough stock is available, false otherwise.
     */
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);

    /**
     * Finds an inventory item by its SKU code.
     *
     * @param skuCode The SKU code of the product.
     * @return An Optional containing the Inventory object if found, otherwise empty.
     */
    Optional<Inventory> findBySkuCode(String skuCode);
}
