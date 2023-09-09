package org.example.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductCreateDTO {
    private String name;
    private double price;
    private String description;
    private MultipartFile[] productImages;
    private int categoryId;
}