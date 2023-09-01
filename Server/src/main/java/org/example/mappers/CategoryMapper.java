package org.example.mappers;

import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Iterator;
import java.util.List;

// convert to an abstract class

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // Entity to DTO mappings
    CategoryItemDTO entityToItemDTO(CategoryEntity entity);

    // DTO to Entity mappings
    CategoryEntity createDTOToEntity(CategoryCreateDTO dto);

    // Lists mappings
    List<CategoryItemDTO> entitiesToItemDTOs(List<CategoryEntity> entities);
}