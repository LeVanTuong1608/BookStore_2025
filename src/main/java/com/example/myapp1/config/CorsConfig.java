package com.example.myapp1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     // Định nghĩa cấu hình CORS
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         // Cho phép tất cả các request từ localhost:3000 (frontend của bạn)
//         // registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
//         // .allowedOrigins("http://localhost:3000") // Địa chỉ của frontend
//         // .allowedMethods("GET", "POST", "PUT", "DELETE") // Phương thức HTTP được phép
//         // .allowCredentials(true) // Cho phép gửi cookie
//         // .allowedHeaders("*"); // Cho phép tất cả các
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true);
//         config.addAllowedOrigin("http://localhost:8080");
//         config.addAllowedHeader("*");
//         config.addAllowedMethod("*");
//         source.registerCorsConfiguration("/**", config);
//         return new CorsFilter(source);
//     }
// }


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
