package org.example.mappers;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
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

    @Named("image_xs")
    static String image_xs(String filename) {
        return image(filename, 32);
    }
    @Named("image_sm")
    static String image_sm(String filename) {
        return image(filename, 150);
    }
    @Named("image_md")
    static String image_md(String filename) {
        return image(filename, 300);
    }
    @Named("image_lg")
    static String image_lg(String filename) {
        return image(filename, 600);
    }
    @Named("image_xl")
    static String image_xl(String filename) {
        return image(filename, 1200);
    }

    static String image(String filename, int size) {
        return "http://localhost:8081/uploads/" + size + "_" + filename;
    }
}