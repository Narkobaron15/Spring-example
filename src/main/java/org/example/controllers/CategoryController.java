package org.example.controllers;

import org.example.dto.CategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> index() {
        var result = new ArrayList<CategoryDTO>();
        var item = new CategoryDTO();
        item.setId(1);
        item.setName("Сало");
        item.setImageURL("salo.jpg");
        item.setDescription("Для козаків");
        result.add(item);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
