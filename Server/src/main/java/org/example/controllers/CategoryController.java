package org.example.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.entities.category.CategoryEntity;
import org.example.mappers.CategoryMapper;
import org.example.repositories.CategoryRepo;
import org.example.storage.StorageService;
import org.example.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>Custom controller</h1>
 * <hr>
 * <p>
 *     This controller is used to handle
 *     CRUD operations related to categories
 * </p>
 */

@RestController
@RequestMapping(path = "${apiPrefix}/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "my-api")
public class CategoryController {
    private final CategoryRepo catRepo;
    private final CategoryMapper mapper;
    private final StorageService storage;

    // Create
    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CategoryItemDTO> create(
            @Valid @ModelAttribute CategoryCreateDTO createDTO
    ) {
        String filename = storage.store(createDTO.getImage());

        CategoryEntity entity = mapper.createDTOToEntity(createDTO);
        entity.setImage(filename);
        entity = catRepo.save(entity);

        CategoryItemDTO itemDTO = mapper.entityToItemDTO(entity);
        return ResponseEntity.ok(itemDTO);
    }

    // Read

    /// Read all
    @GetMapping(value = {"", "/"}) // add CategoryItem mapping
    public ResponseEntity<List<CategoryItemDTO>> index() {
        var dtoList = mapper.entitiesToItemDTOs(catRepo.findAll());
        return ResponseEntity.ok(dtoList);
    }

    /// Read by id
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryItemDTO> index(@PathVariable Integer id) {
        // optional (nullable) category entity
        var probableCat = catRepo.findById(id);
        // maps to ItemDTO or bad request
        return probableCat.map(
                entity ->
                ResponseEntity.ok(mapper.entityToItemDTO(entity))
        ).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Update
    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CategoryItemDTO> update(
            @PathVariable Integer id,
            @Valid @ModelAttribute CategoryUpdateDTO dto
    ) {
        var probableOldEntity = catRepo.findById(id);
        if (probableOldEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CategoryEntity entity = mapper.updateDTOToEntity(dto);
        entity.setId(id);

        String currentImg = probableOldEntity.get().getImage();
        if (dto.getImage() == null) {
            entity.setImage(currentImg);
        } else {
            storage.delete(currentImg);
            String newImg = storage.store(dto.getImage());
            entity.setImage(newImg);
        }

        entity = catRepo.save(entity);

        CategoryItemDTO updatedCatDTO = mapper.entityToItemDTO(entity);
        return ResponseEntity.ok(updatedCatDTO);
    }

    // Delete
    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        var probableOldEntity = catRepo.findById(id);
        if (probableOldEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        storage.delete(probableOldEntity.get().getImage());
        catRepo.deleteById(id);
        return ResponseEntity.ok(JsonUtils.successDeleteJO.toString());
    }
}
