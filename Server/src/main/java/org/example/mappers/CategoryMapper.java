package org.example.mappers;

import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // Entity to DTO mappings
    CategoryItemDTO entityToItemDTO(CategoryEntity entity);

    CategoryCreateDTO entityToCreateDTO(CategoryEntity entity);


    // DTO to Entity mappings
    CategoryEntity createDTOToEntity(CategoryCreateDTO dto);

    CategoryEntity itemDTOToEntity(CategoryItemDTO dto);


    // Lists mappings
    List<CategoryEntity> itemDTOsToEntities(List<CategoryItemDTO> dtos);

    List<CategoryEntity> createDTOsToEntities(List<CategoryCreateDTO> dtos);

    List<CategoryItemDTO> entitiesToItemDTOs(List<CategoryEntity> entities);

    List<CategoryCreateDTO> entitiesToCreateDTOs(List<CategoryEntity> entities);
}