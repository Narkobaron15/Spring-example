package org.example.storage

import org.springframework.context.annotation.Configuration

@Configuration
open class StorageProperties {
    var location: String = "uploads"
}
