package org.example.storage

import lombok.Getter
import lombok.Setter
import org.springframework.context.annotation.Configuration

@Configuration
open class StorageProperties {
    val location = "uploads"
}
