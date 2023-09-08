package org.example.controllers

import org.example.dto.product.ProductCreateDTO
import org.example.dto.product.ProductItemDTO
import org.example.dto.product.ProductUpdateDTO
import org.example.mappers.ProductMapper
import org.example.repositories.ProductRepo
import org.example.storage.StorageService
import org.example.utils.JsonUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Placeholder for CRUD operations of the product controller
 * TODO: To implement everything soon
 */
@RestController
@RequestMapping("\${apiPrefix}/products")
open class ProductController (
    private val productRepo: ProductRepo,
    private val productMapper: ProductMapper,
    private val storage: StorageService,
) {
    // Create a new product
    @PostMapping
    fun createProduct(@RequestBody createDTO: ProductCreateDTO)
            : ResponseEntity<ProductItemDTO> {
        val entity = productMapper.toProductEntity(createDTO)

        // add here the logic of creating images (strictly necessary)
//        for (file in createDTO.productImages) {
//
//        }
        val createdProduct = productRepo.save(entity)

        val itemDTO = productMapper.toDto(createdProduct)
        return ResponseEntity.ok(itemDTO)
    }

    // Retrieve all products
    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductItemDTO>> {
        val products = productRepo.findAll()
        val dtos = productMapper.toDtoList(products)
        return ResponseEntity.ok().body(dtos)
    }

    // Retrieve a single product by ID
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long)
            : ResponseEntity<ProductItemDTO> {
        val optionalProduct = productRepo.findById(productId)

        return if (optionalProduct.isPresent)
            ResponseEntity.ok(productMapper.toDto(optionalProduct.get()))
        else ResponseEntity.notFound().build()
    }

    // Update a product by ID
    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody dto: ProductUpdateDTO
    ): ResponseEntity<ProductItemDTO> {
        if (productRepo.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }

        // add here the logic of updating images (strictly necessary)
        val newProduct = productMapper.toProductEntity(dto)
        newProduct.id = productId
        productRepo.save(newProduct)

        val updatedDTO = productMapper.toDto(newProduct)
        return ResponseEntity.ok(updatedDTO)
    }

    // Delete a product by ID
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long)
            : ResponseEntity<String> {
        val optionalProduct = productRepo.findById(productId);

        if (optionalProduct.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        productRepo.deleteById(productId);
        return ResponseEntity.ok(JsonUtils.successDeleteJO.toString())
    }
}