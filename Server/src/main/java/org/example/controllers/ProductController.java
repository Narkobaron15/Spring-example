package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductUpdateDTO;
import org.example.entities.ProductEntity;
import org.example.entities.ProductImageEntity;
import org.example.mappers.ProductMapper;
import org.example.repositories.ProductImageRepo;
import org.example.repositories.ProductRepo;
import org.example.storage.StorageService;
import org.example.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${apiPrefix}/products")
public class ProductController {
    private final ProductRepo prodRepo;
    private final ProductImageRepo imgRepo;
    private final ProductMapper mapper;
    private final StorageService storage;

    private void SaveImages(ProductEntity product, MultipartFile[] files) {
        int i = 0;
        for (MultipartFile file : files) {
            String path = storage.store(file);
            ProductImageEntity img = ProductImageEntity
                    .builder()
                    .filename(path)
                    .product(product)
                    .priority(i++)
                    .build();
            imgRepo.save(img);
        }
    }

    // Create a new product
    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ProductItemDTO> createProduct(
            @ModelAttribute ProductCreateDTO createDTO
    ) {
        ProductEntity entity = mapper.toProductEntity(createDTO);

        entity = prodRepo.save(entity);
        SaveImages(entity, createDTO.getProductImages());

        ProductItemDTO itemDTO = mapper.toDto(entity);
        return ResponseEntity.ok(itemDTO);
    }

    // Retrieve all products
    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<ProductItemDTO>> getAllProducts() {
        List<ProductEntity> products = prodRepo.findAll();
        List<ProductItemDTO> dtos = mapper.toDtoList(products);
        return ResponseEntity.ok(dtos);
    }

    // Retrieve a single product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductItemDTO> getProductById(
            @PathVariable Long productId
    ) {
        var optionalProduct = prodRepo.findById(productId);
        return optionalProduct.map(
                productEntity ->
                ResponseEntity.ok(mapper.toDto(productEntity))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a product by ID
    @PutMapping(
            value = "/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ProductItemDTO> updateProduct(
            @PathVariable Long productId, 
            @ModelAttribute ProductUpdateDTO dto
    ) {
        if (!prodRepo.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }

        // deleting previous pics
        Long[] picsToRemove = dto.getRemoveProductImages();
        if (picsToRemove != null)
            imgRepo.deleteAllById(List.of(picsToRemove));

        ProductEntity newProduct = mapper.toProductEntity(dto);
        newProduct.setId(productId);

        // adding new pics
        MultipartFile[] picsToAdd = dto.getNewProductImages();
        if (picsToAdd != null)
            SaveImages(newProduct, picsToAdd);

        // saving a product
        newProduct = prodRepo.save(newProduct);

        ProductItemDTO updatedDTO = mapper.toDto(newProduct);
        return ResponseEntity.ok(updatedDTO);
    }

    // Delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long productId
    ) {
        var optionalProduct = prodRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        prodRepo.deleteById(productId);
        return ResponseEntity.ok(JsonUtils.successDeleteJO.toString());
    }
}
