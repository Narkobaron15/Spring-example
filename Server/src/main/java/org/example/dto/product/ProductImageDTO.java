package org.example.dto.product;

import lombok.Data;
import org.example.dto.BaseImageDTO;

@Data
public class ProductImageDTO extends BaseImageDTO {
    private Long id;
    private Long priority;
}

