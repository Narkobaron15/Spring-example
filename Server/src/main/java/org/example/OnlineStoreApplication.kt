package org.example;

import org.example.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.example.repositories"})
public class OnlineStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService service) {
        return args -> {
            try {
                service.init();
            } catch (Exception ex) {
                System.out.println("[ERROR] " + ex.getMessage());
            }
        };
    }
}
