package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mappers.CategoryMapper;
import org.example.repositories.CategoryRepo;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

/**
 * <b>Custom controller</b> <p>
 * Add correct mappings, don't respond with jsons or db entities
 */

@RestController
@RequestMapping(path = "${apiPrefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepo catRepo;
    private final CategoryMapper mapper;

    // Fix the problem with accepting the POST request

    // Create
    @PostMapping("/create")
    public ResponseEntity<CategoryItemDTO> create(
            @Valid @ModelAttribute CategoryCreateDTO createDTO
    ) {
        CategoryEntity newEntity = mapper.createDTOToEntity(createDTO);
        catRepo.save(newEntity);
        CategoryItemDTO itemDTO = mapper.entityToItemDTO(newEntity);
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    // Read

    /// Read all
    @GetMapping(value = {"", "/"}) // add CategoryItem mapping
    public ResponseEntity<List<CategoryItemDTO>> index() {
        List<CategoryItemDTO> dtoList = mapper.entitiesToItemDTOs(catRepo.findAll());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /// Read by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryItemDTO> index(@PathVariable Integer id) {
        // optional (nullable) category entity
        var cat = catRepo.findById(id);
        // maps to ItemDTO or bad request
        return cat.map(entity -> new ResponseEntity(mapper.entityToItemDTO(entity), HttpStatus.OK))
                .orElseGet(() -> BadRequestFor(id));
    }

    // Update
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryItemDTO> update(@PathVariable Integer id, @Valid @ModelAttribute CategoryCreateDTO dto) {
        if (!catRepo.existsById(id)) {
            return BadRequestFor(id);
        }

        CategoryEntity category = mapper.createDTOToEntity(dto);
        category.setId(id);
        catRepo.save(category);

        CategoryItemDTO updatedCatDTO = mapper.entityToItemDTO(category);
        return new ResponseEntity<>(updatedCatDTO, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!catRepo.existsById(id)) {
            return BadRequestFor(id);
        }

        catRepo.deleteById(id);

        var jo = new JSONObject();
        jo.put("message", "Deleted successfully");
        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
    }

    private ResponseEntity BadRequestFor(Integer id) {
        var jo = new JSONObject();
        jo.put("status", "400 Bad Request");
        jo.put("message", "The item with id \"" + id + "\" doesn't exist");
        return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
    }
}
