package org.example.dto;

import lombok.Data;

@Data
public class CategoryItemDTO {
    private int id;
    private String name;
    private ImageDTO image;
    private String description;
}
