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
    public ResponseEntity<CategoryItemDTO> create(@Valid @ModelAttribute CategoryCreateDTO dto) {
        var newEntity = mapper.createDTOToEntity(dto);
        catRepo.save(newEntity);
        return new ResponseEntity<>(mapper.entityToItemDTO(newEntity), HttpStatus.OK);
    }

    // Read

    /// Read all
    @GetMapping(value = {"", "/"}) // add CategoryItem mapping
    public ResponseEntity<List<CategoryItemDTO>> index() {
        return new ResponseEntity<>(mapper.entitiesToItemDTOs(catRepo.findAll()), HttpStatus.OK);
    }

    /// Read by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryItemDTO> index(@PathVariable Integer id) {
        var cat = catRepo.findById(id);
        return cat.map(entity -> new ResponseEntity(mapper.entityToItemDTO(entity), HttpStatus.OK))
                .orElseGet(() -> BadRequestFor(id));
    }

    // Update
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryItemDTO> update(@PathVariable Integer id, @Valid @ModelAttribute CategoryCreateDTO dto) {
        if (catRepo.existsById(id)) {
            var category = mapper.createDTOToEntity(dto);
            category.setId(id);
            catRepo.save(category);

            return new ResponseEntity<>(mapper.entityToItemDTO(category), HttpStatus.OK);
        } else {
            return BadRequestFor(id);
        }
    }

    // Delete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (catRepo.existsById(id)) {
            catRepo.deleteById(id);
            var jo = new JSONObject();
            jo.put("message", "Deleted successfully");
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
        } else {
            return BadRequestFor(id);
        }
    }

    private ResponseEntity BadRequestFor(Integer id) {
        var jo = new JSONObject();
        jo.put("status", "400 Bad Request");
        jo.put("message", "The item with id \"" + id + "\" doesn't exist");
        return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
    }
}
