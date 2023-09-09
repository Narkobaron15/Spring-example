package org.example.dto.product;

import lombok.Data;
import org.example.entities.CategoryEntity;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductUpdateDTO {
    private String name;
    private double price;
    private String description;
    private int[] oldProductImages;
    private MultipartFile[] newProductImages;
    private CategoryEntity category;
}
