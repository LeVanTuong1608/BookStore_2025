package com.example.myapp1.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "domnsybxh",
                "api_key", "986561924766832",
                "api_secret", "GlOB--NKE0BCWPxM6kA6olU4Kag",
                "secure", true));
    }
}
