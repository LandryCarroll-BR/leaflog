package com.landrycarroll.leaflog;

import com.landrycarroll.leaflog.infrastructure.DynamicDbInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class LeaflogApplication {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        SpringApplication app = new SpringApplication(LeaflogApplication.class);
        app.addInitializers(new DynamicDbInitializer());
        app.run(args);

        openBrowser("http://localhost:8080");
    }

    private static void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Desktop not supported. Please open " + url + " manually.");
            }
        } catch (Exception e) {
            System.err.println("Failed to open browser: " + e.getMessage());
        }
    }
}