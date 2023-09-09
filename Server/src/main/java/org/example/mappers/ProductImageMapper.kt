package org.example.mappers

import org.example.dto.product.ProductImageDTO
import org.example.entities.ProductImageEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ProductImageMapper {
    fun productImageToItemDTO(productImage: ProductImageEntity): ProductImageDTO
}