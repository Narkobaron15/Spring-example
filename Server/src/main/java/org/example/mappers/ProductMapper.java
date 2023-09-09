package org.example.mappers;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductUpdateDTO;
import org.example.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends ImageMapper {
    ProductEntity toProductEntity(ProductCreateDTO createDto);
    ProductEntity toProductEntity(ProductUpdateDTO updateDto);

    @Mapping(target = "productImages", ignore = true)
    ProductItemDTO toDto(ProductEntity entity);

    List<ProductItemDTO> toDtoList(List<ProductEntity> entities);
}
