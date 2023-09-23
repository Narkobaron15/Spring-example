package org.example.dto.product;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductUpdateDTO {
    private String name;
    private double price;
    private String description;
    @Nullable
    private Long[] removeProductImages;
    @Nullable
    private MultipartFile[] newProductImages;
    private int categoryId;
}
