package com.akentech.microservices.inventory;

import com.akentech.microservices.common.dto.InventoryRequest;
import com.akentech.microservices.inventory.model.Inventory;
import com.akentech.microservices.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		inventoryRepository.deleteAll(); // Clean database before each test

		// Pre-populate the test database with inventory items
		Inventory item1 = new Inventory(null, "SKU123", 10);
		Inventory item2 = new Inventory(null, "SKU456", 5);
		inventoryRepository.saveAll(List.of(item1, item2));
	}

	/**
	 * Test case to check if an item is in stock
	 */
	@Test
	void shouldCheckInventoryInStock() throws Exception {
		InventoryRequest request = new InventoryRequest("SKU123", 5);

		mockMvc.perform(post("/api/inventory/check")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.skuCode").value("SKU123"))
				.andExpect(jsonPath("$.inStock").value(true));
	}

	/**
	 * Test case to check if an item is out of stock
	 */
	@Test
	void shouldCheckInventoryOutOfStock() throws Exception {
		InventoryRequest request = new InventoryRequest("SKU123", 20); // Requesting more than available

		mockMvc.perform(post("/api/inventory/check")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.skuCode").value("SKU123"))
				.andExpect(jsonPath("$.inStock").value(false));
	}

	/**
	 * Test case to check inventory with invalid quantity (negative number)
	 */
	@Test
	void shouldReturnBadRequestForInvalidQuantity() throws Exception {
		InventoryRequest request = new InventoryRequest("SKU123", -1); // Invalid quantity

		mockMvc.perform(post("/api/inventory/check")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.quantity").value("Quantity must be at least 0"));
	}

	/**
	 * Test case to retrieve all inventory items
	 */
	@Test
	void shouldGetAllInventoryItems() throws Exception {
		mockMvc.perform(get("/api/inventory"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2)) // Expecting two items
				.andExpect(jsonPath("$[*].skuCode", containsInAnyOrder("SKU123", "SKU456")));
	}

	/**
	 * Test case to retrieve an inventory item by ID
	 */
	@Test
	void shouldGetInventoryItemById() throws Exception {
		Inventory inventory = inventoryRepository.findAll().getFirst(); // Get first inventory item

		mockMvc.perform(get("/api/inventory/" + inventory.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.skuCode").value(inventory.getSkuCode()));
	}

	/**
	 * Test case to retrieve an inventory item by SKU code
	 */
	@Test
	void shouldGetInventoryItemBySkuCode() throws Exception {
		mockMvc.perform(get("/api/inventory/sku/SKU123"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.skuCode").value("SKU123"));
	}

	/**
	 * Test case to handle a non-existent inventory item lookup by ID
	 */
	@Test
	void shouldReturnNotFoundForNonExistentItemById() throws Exception {
		mockMvc.perform(get("/api/inventory/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Inventory item not found with id: 999"));
	}

	/**
	 * Test case to handle a non-existent inventory item lookup by SKU
	 */
	@Test
	void shouldReturnNotFoundForNonExistentItemBySkuCode() throws Exception {
		mockMvc.perform(get("/api/inventory/sku/NON_EXISTENT"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Inventory item not found with SKU code: NON_EXISTENT"));
	}
}
