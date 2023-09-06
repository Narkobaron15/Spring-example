package org.example.controllers

import org.example.dto.product.ProductItemDTO
import org.example.mappers.ProductMapper
import org.example.repositories.ProductRepo
import org.example.storage.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Placeholder for CRUD operations of the product controller
 * TODO: To implement everything soon
 */
@RestController
@RequestMapping("\${apiPrefix}/products")
class ProductController (
    private val prodRepo: ProductRepo,
    private val prodMapper: ProductMapper,
    private val storage: StorageService,
) {


    // Read by id
    @GetMapping(value = ["", "/"])
    fun index(): ResponseEntity<List<ProductItemDTO>> {
        return ResponseEntity.ofNullable(null);
    }


}