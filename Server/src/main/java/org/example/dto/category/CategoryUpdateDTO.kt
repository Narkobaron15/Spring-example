package org.example.dto.category

import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile

open class CategoryUpdateDTO {
    lateinit var name: @NotBlank(message = "Назва є обов'язковою") String
    lateinit var description: @NotBlank(message = "Опис є обов'язковим") String
    var image: MultipartFile? = null
}
