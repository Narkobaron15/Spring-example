package org.example.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * CORS configuration class
 *
 * Allows requests from any origin for all specified endpoints
 */
@Configuration
open class CorsConfig {
    @Bean
    open fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()

        // specify cors config
        val config = CorsConfiguration()
        config.allowCredentials = false //updated to false
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("HEAD")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("DELETE")

        // register cors configuration for specified endpoints (by pattern)
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}