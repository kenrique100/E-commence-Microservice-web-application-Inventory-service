package com.akentech.microservices.inventory.exception;

/**
 * Exception thrown when a requested inventory item is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
