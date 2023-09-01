package org.example.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreateDTO {
    @NotBlank(message = "Назва є обов'язковою")
    private String name;
//    @NotBlank(message = "Картинка є обов'язковою")
//    private String imageURL;
    @NotBlank(message = "Опис є обов'язковим")
    private String description;

    @Nonnull
    private MultipartFile image;
}