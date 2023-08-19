package org.example.controllers;

import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryCreateDTO;
import org.example.entities.CategoryEntity;
import org.example.repositories.ICategoryRepo;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Custom controller
 * Add correct mappings, don't respond with jsons or db entities
 */

@RestController
@RequestMapping(path = "${apiPrefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryRepo catRepo;

    // Create
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CategoryCreateDTO dto) {
        var newEntity = new CategoryEntity();
        newEntity.setName(dto.getName());
        newEntity.setImageURL(dto.getImageURL());
        newEntity.setDescription(dto.getDescription());

        catRepo.save(newEntity);

        return new ResponseEntity<>(ToJson(newEntity), HttpStatus.OK);
    }

    // Read

    //# Read all
    @GetMapping("/") // add CategoryItem mapping
    public ResponseEntity<List<CategoryEntity>> index() {
        return new ResponseEntity<>(catRepo.findAll(), HttpStatus.OK);
    }

    //#Read by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> index(@PathVariable Integer id) {
        var cat = catRepo.findById(id);
        return cat.map(categoryEntity -> new ResponseEntity<>(ToJson(categoryEntity), HttpStatus.OK))
                .orElseGet(() -> BadRequestFor(id));
    }

    // Update
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody CategoryCreateDTO dto) {
        var categoryResult = catRepo.findById(id);

        if (categoryResult.isPresent()) {
            var category = categoryResult.get();
            category.setName(dto.getName());
            category.setImageURL(dto.getImageURL());
            category.setDescription(dto.getDescription());

            catRepo.save(category);

            return new ResponseEntity<>(ToJson(category), HttpStatus.OK);
        } else {
            return BadRequestFor(id);
        }
    }

    // Delete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (catRepo.existsById(id)) {
            catRepo.deleteById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } else {
            return BadRequestFor(id);
        }
    }

    private ResponseEntity<String> BadRequestFor(Integer id) {
        var jo = new JSONObject();
        jo.put("status", "400 Bad Request");
        jo.put("message", "The item with id \"" + id + "\" doesn't exist");
        return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
    }
    
    private String ToJson(Object value) {
        return ToJson(value);
    }
}
