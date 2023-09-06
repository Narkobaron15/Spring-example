package org.example.dto.category;

import lombok.Data;
import org.example.dto.ImageDTO;

@Data
public class CategoryItemDTO {
    private int id;
    private String name;
    private ImageDTO image;
    private String description;
}
