package org.example.dto.product

import org.example.entities.CategoryEntity

class ProductItemDTO {
    var id: Long = 0
    lateinit var name: String
    var price = 0.0
    lateinit var description: String
    lateinit var productImages: List<ProductImageDTO>
    lateinit var category: CategoryEntity
}