package com.landrycarroll.leaflog.infrastructure;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Scanner;

/**
 * {@code DynamicDbInitializer} is a custom Spring {@link ApplicationContextInitializer}
 * that allows the user to input a dynamic path to an SQLite database at application startup.
 * <p>
 * This initializer prompts the user via the console for a database file path, processes it,
 * and sets it as the {@code spring.datasource.url} system property, which Spring Boot uses
 * to configure the datasource.
 * </p>
 * <p><strong>Usage:</strong> Register this initializer in {@code spring.factories} or in the application's main method.</p>
 */
public class DynamicDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * Prompts the user to enter the path to the SQLite database, ensures the path is absolute,
     * and sets the {@code spring.datasource.url} system property with the corresponding JDBC URL.
     *
     * @param context the configurable application context that is being initialized
     */
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
