package com.landrycarroll.leaflog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaflogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeaflogApplication.class, args);

        System.out.println("Leaflog application started on http://localhost:8080");
    }
}