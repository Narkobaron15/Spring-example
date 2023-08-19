package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryCreateDTO;
import org.example.entities.CategoryEntity;
import org.example.repositories.ICategoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Custom controller
 */

@RestController
@RequestMapping(path = "${apiPrefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryRepo catRepo;

    // Create
    @PostMapping("/create")
    public ResponseEntity<CategoryEntity> create(@RequestBody CategoryCreateDTO dto) {
        var newEntity = new CategoryEntity();
        newEntity.setName(dto.getName());
        newEntity.setImageURL(dto.getImageURL());
        newEntity.setDescription(dto.getDescription());

        catRepo.save(newEntity);

        return new ResponseEntity<>(newEntity, HttpStatus.OK);
    }

    // Read

    //# Read all
    @GetMapping("/") // add CategoryItem mapping
    public ResponseEntity<List<CategoryEntity>> index() {
        return new ResponseEntity<>(catRepo.findAll(), HttpStatus.OK);
    }

    //#Read by id
    @GetMapping("/{id}") // add CategoryItem mapping
    public ResponseEntity index(@PathVariable Integer id) {
        var cat = catRepo.findById(id);
        if (cat.isPresent()) {
            return new ResponseEntity<>(cat.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("404 Not Found", HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody CategoryCreateDTO dto) {
        var categoryResult = catRepo.findById(id);

        if (categoryResult.isPresent()) {
            var category = categoryResult.get();
            category.setName(dto.getName());
            category.setImageURL(dto.getImageURL());
            category.setDescription(dto.getDescription());

            catRepo.save(category);

            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (catRepo.existsById(id)) {
            catRepo.deleteById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An item with the requested id doesn't exist.", HttpStatus.BAD_REQUEST);
        }
    }
}
