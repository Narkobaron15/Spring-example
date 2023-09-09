package org.example.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class StorageProperties {
    private String location = "uploads";
}
