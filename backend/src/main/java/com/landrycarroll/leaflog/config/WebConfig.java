package com.landrycarroll.leaflog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class that customizes Spring MVC settings.
 * <p>
 * This class enables and configures Cross-Origin Resource Sharing (CORS)
 * to allow requests from the frontend application (e.g., running on localhost:3000)
 * to access backend endpoints.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure global CORS settings.
     * <p>
     * This method allows cross-origin requests from the specified origin (localhost:3000),
     * supports standard HTTP methods (GET, POST, PUT, DELETE, OPTIONS), and permits all headers.
     * Credentials such as cookies and authorization headers are also allowed.
     *
     * @param registry the {@link CorsRegistry} used to define CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:3000") // Frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // If you're using cookies
    }
}
