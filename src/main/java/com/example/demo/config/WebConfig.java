package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//allows (Cross-Origin Resource Sharing) meaning other servers can access mine works well for if front end is held elsewhere
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS Configuration Loaded");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342") // Replace with real url when deployed so only my website can ping my server
                //.allowedOrigins("*") // Allow all origins TEMPORARILY
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
