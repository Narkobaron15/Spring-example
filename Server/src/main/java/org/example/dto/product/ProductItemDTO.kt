package org.example.dto.product

import org.example.dto.ImageDTO
import org.example.entities.CategoryEntity
import org.example.entities.ProductImageEntity

class ProductItemDTO {
    var id: Long = 0
    lateinit var name: String
    var price = 0.0
    lateinit var description: String
    lateinit var productImages: List<ImageDTO>
    lateinit var category: CategoryEntity
}