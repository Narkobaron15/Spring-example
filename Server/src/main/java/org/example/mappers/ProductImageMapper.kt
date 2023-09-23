package org.example.mappers

import org.example.dto.product.ProductImageDTO
import org.example.entities.product.ProductImageEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface ProductImageMapper : ImageMapper {
    @Mapping(source = "filename", target = "xs", qualifiedByName = ["image_xs"])
    @Mapping(source = "filename", target = "sm", qualifiedByName = ["image_sm"])
    @Mapping(source = "filename", target = "md", qualifiedByName = ["image_md"])
    @Mapping(source = "filename", target = "lg", qualifiedByName = ["image_lg"])
    @Mapping(source = "filename", target = "xl", qualifiedByName = ["image_xl"])
    fun productImageToDTO(productImage: ProductImageEntity): ProductImageDTO

    fun productImagesToDTOs(productImages: List<ProductImageEntity>) : List<ProductImageDTO>
}