package org.example.mappers;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductUpdateDTO;
import org.example.entities.category.CategoryEntity;
import org.example.entities.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends ProductImageMapper {
    @Mapping(target = "category", source = "categoryId", qualifiedByName = {"categoryIdToCategory"})
    ProductEntity toProductEntity(ProductCreateDTO createDto);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = {"categoryIdToCategory"})
    ProductEntity toProductEntity(ProductUpdateDTO updateDto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "images", source = "productImages")
    ProductItemDTO toDto(ProductEntity entity);

    List<ProductItemDTO> toDtoList(List<ProductEntity> entities);

    @Named("categoryIdToCategory")
    static CategoryEntity categoryIdToCategory(int categoryId) {
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryId);
        return category;
    }
}
