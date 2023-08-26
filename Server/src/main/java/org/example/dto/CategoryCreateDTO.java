package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateDTO {
    @NotBlank(message = "Назва є обов'язковою")
    private String name;
    @NotBlank(message = "Картинка є обов'язковою")
    private String imageURL;
    @NotBlank(message = "Опис є обов'язковим")
    private String description;
}