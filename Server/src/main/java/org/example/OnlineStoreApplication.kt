package org.example

import org.example.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.example.repositories"])
open class OnlineStoreApplication {
    @Bean
    open fun init(service: StorageService): CommandLineRunner {
        return CommandLineRunner {
            try {
                service.init()
            } catch (ex: Exception) {
                println("[ERROR] " + ex.message)
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(OnlineStoreApplication::class.java, *args)
}
