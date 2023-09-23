package org.example.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configuration class for web resources
 *
 * Opens access to the uploads folder
 */
@Configuration
open class WebResourcesConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads\\")
    }
}
