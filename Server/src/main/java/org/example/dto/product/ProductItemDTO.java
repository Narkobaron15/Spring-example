package org.example.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductItemDTO {
    private long id;
    private String name;
    private double price;
    private String description;
    private List<ProductImageDTO> images;
    private int categoryId;
    private String categoryName;
}
