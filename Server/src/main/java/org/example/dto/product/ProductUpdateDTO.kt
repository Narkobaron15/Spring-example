package org.example.dto.product

import org.example.entities.CategoryEntity
import org.springframework.web.multipart.MultipartFile

class ProductUpdateDTO {
    lateinit var name: String
    var price = 0.0
    lateinit var description: String
    lateinit var oldProductImages: IntArray
    lateinit var newProductImages: Array<MultipartFile>
    lateinit var category: CategoryEntity
}