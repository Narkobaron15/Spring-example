package org.example.mappers;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends ImageMapper {
    // Entity to DTO mappings
    @Mapping(source = "image", target = "image.xs", qualifiedByName = "image_xs")
    @Mapping(source = "image", target = "image.sm", qualifiedByName = "image_sm")
    @Mapping(source = "image", target = "image.md", qualifiedByName = "image_md")
    @Mapping(source = "image", target = "image.lg", qualifiedByName = "image_lg")
    @Mapping(source = "image", target = "image.xl", qualifiedByName = "image_xl")
    CategoryItemDTO entityToItemDTO(CategoryEntity entity);

    // DTO to Entity mappings
    @Mapping(source = "image", target = "image", ignore = true)
    CategoryEntity createDTOToEntity(CategoryCreateDTO dto);
    @Mapping(source = "image", target = "image", ignore = true)
    CategoryEntity updateDTOToEntity(CategoryUpdateDTO dto);

    // Lists mappings
    List<CategoryItemDTO> entitiesToItemDTOs(List<CategoryEntity> entities);
}