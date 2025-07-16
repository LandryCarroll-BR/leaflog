package com.landrycarroll.leaflog.infrastructure;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Scanner;

public class DynamicDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment env = context.getEnvironment();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to SQLite database: ");
        String dbPath = scanner.nextLine().trim();

        // If relative path, make absolute
        if (!dbPath.startsWith("/")) {
            dbPath = System.getProperty("user.dir") + "/" + dbPath;
        }

        System.out.println("Using database: " + dbPath);

        // Inject into Spring Environment
        System.setProperty("spring.datasource.url", "jdbc:sqlite:" + dbPath);
    }
}
