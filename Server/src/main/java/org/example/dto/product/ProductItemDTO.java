package org.example.dto.product;

import lombok.Data;
import org.example.entities.CategoryEntity;

import java.util.List;

@Data
public class ProductItemDTO {
    private long id;
    private String name;
    private double price;
    private String description;
    private List<ProductImageDTO> productImages;
    private CategoryEntity category;
}
