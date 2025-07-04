package ru.bsu.cad.lab.reader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ResourceFileReader implements Reader {
    private final ResourceLoader resourceLoader;
    private final String filePath = "classpath:product.csv";

    public ResourceFileReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String read() {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }
}
