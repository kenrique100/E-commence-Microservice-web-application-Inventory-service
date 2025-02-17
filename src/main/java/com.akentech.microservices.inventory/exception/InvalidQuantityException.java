package com.akentech.microservices.inventory.exception;

/**
 * Exception thrown when an invalid quantity is requested.
 */
public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(String message) {
        super(message);
    }
}