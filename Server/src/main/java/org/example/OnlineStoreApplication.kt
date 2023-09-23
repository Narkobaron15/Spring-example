package org.example

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.example.services.SeedService
import org.example.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * https://www.baeldung.com/spring-boot-api-key-secret
 * https://www.baeldung.com/spring-security-csrf
 */

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.example.repositories"])
@SecurityScheme(name = "my-api", scheme = "bearer", type = SecuritySchemeType.HTTP, `in` = SecuritySchemeIn.HEADER)
open class OnlineStoreApplication {
    @Bean
    open fun init(service: StorageService, seeder: SeedService): CommandLineRunner {
        return CommandLineRunner {
            try {
                service.init()
                seeder.seedRoleData()
                seeder.seedUserData()
            } catch (ex: Exception) {
                println("[ERROR] " + ex.message)
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(OnlineStoreApplication::class.java, *args)
}
