package com.atlashorticole.product_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cloudinary.Cloudinary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name:demo}")
    private String cloudName;

    @Value("${cloudinary.api-key:key}")
    private String apiKey;

    @Value("${cloudinary.api-secret:secret}")
    private String apiSecret;

    @Bean
    @Profile("!test")
    public Cloudinary cloudinary() {
        log.info("=== CLOUDINARY CONFIGURATION ===");
        log.info("Cloud Name: {}", cloudName);
        log.info("API Key: {}", mask(apiKey));

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", "true");
        return new Cloudinary(config);
    }

    @Bean
    @Profile("test")
    public Cloudinary cloudinaryMock() {
        log.info("=== CLOUDINARY MOCK BEAN FOR TESTS ===");

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "test-cloud");
        config.put("api_key", "test-key");
        config.put("api_secret", "test-secret");
        config.put("secure", "false");
        return new Cloudinary(config);
    }

    private String mask(String value) {
        if (value == null || value.length() <= 4)
            return "****";
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }
}