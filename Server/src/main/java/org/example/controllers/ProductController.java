package org.example.controllers;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductUpdateDTO;
import org.example.entities.ProductEntity;
import org.example.mappers.ProductMapper;
import org.example.repositories.ProductRepo;
import org.example.storage.StorageService;
import org.example.utils.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${apiPrefix}/products")
public class ProductController {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final StorageService storage;

    public ProductController(ProductRepo productRepo, ProductMapper productMapper, StorageService storage) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.storage = storage;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductItemDTO> createProduct(@RequestBody ProductCreateDTO createDTO) {
        ProductEntity entity = productMapper.toProductEntity(createDTO);

        // add here the logic of creating images (strictly necessary)
//        for (file in createDTO.getProductImages()) {
//
//        }
        ProductEntity createdProduct = productRepo.save(entity);

        ProductItemDTO itemDTO = productMapper.toDto(createdProduct);
        return ResponseEntity.ok(itemDTO);
    }

    // Retrieve all products
    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> getAllProducts() {
        List<ProductEntity> products = productRepo.findAll();
        List<ProductItemDTO> dtos = productMapper.toDtoList(products);
        return ResponseEntity.ok(dtos);
    }

    // Retrieve a single product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductItemDTO> getProductById(@PathVariable Long productId) {
        Optional<ProductEntity> optionalProduct = productRepo.findById(productId);

        return optionalProduct
                .map(productEntity -> ResponseEntity.ok(productMapper.toDto(productEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a product by ID
    @PutMapping("/{productId}")
    public ResponseEntity<ProductItemDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateDTO dto) {
        if (!productRepo.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }

        // add here the logic of updating images (strictly necessary)
        ProductEntity newProduct = productMapper.toProductEntity(dto);
        newProduct.setId(productId);
        productRepo.save(newProduct);

        ProductItemDTO updatedDTO = productMapper.toDto(newProduct);
        return ResponseEntity.ok(updatedDTO);
    }

    // Delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        Optional<ProductEntity> optionalProduct = productRepo.findById(productId);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productRepo.deleteById(productId);
        return ResponseEntity.ok(JsonUtils.successDeleteJO.toString());
    }
}
