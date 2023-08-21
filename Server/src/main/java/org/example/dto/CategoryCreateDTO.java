package org.example.dto;

import lombok.Data;

@Data
public class CategoryCreateDTO {
    private String name;
    private String imageURL;
    private String description;
}