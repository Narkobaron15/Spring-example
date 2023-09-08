package org.example.mappers

import org.example.dto.product.ProductCreateDTO
import org.example.dto.product.ProductItemDTO
import org.example.dto.product.ProductUpdateDTO
import org.example.entities.ProductEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ProductMapper : ImageMapper {
    fun toProductEntity(createDto: ProductCreateDTO): ProductEntity
    fun toProductEntity(updateDto: ProductUpdateDTO): ProductEntity
    fun toDto(entity: ProductEntity): ProductItemDTO
    fun toDtoList(entities: List<ProductEntity>): List<ProductItemDTO>
}
