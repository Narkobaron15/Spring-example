package org.example.dto.category;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryUpdateDTO {
    @NotBlank(message = "Назва є обов'язковою")
    private String name;

    @NotBlank(message = "Опис є обов'язковим")
    private String description;

    @Nullable
    private MultipartFile image;
}
