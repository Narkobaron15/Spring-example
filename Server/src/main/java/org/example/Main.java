package org.example;

import org.example.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService service) {
        return (args -> {
            try {
                service.init();
            }
            catch (Exception ex) {
                System.out.println("[ERROR] " + ex.getMessage());
            }
        });
    }
}