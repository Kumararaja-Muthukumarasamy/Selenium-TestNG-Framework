package com.framework.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        try (InputStream iStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (iStream == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            prop.load(iStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private ConfigReader() {}

    public static String getPropValue(String key) {

        String valueFromSystem = System.getProperty(key);

        if (valueFromSystem != null && !valueFromSystem.isEmpty()) {
            return valueFromSystem;
        }

        String valueFromFile = prop.getProperty(key);

        if (valueFromFile == null) {
            throw new RuntimeException("Property not found: " + key);
        }

        return valueFromFile;
    }

    // ✅ STRICT ENV VALIDATION
    public static String getEnvironmentUrl() {

        String env = getPropValue("env");

        if (env == null || env.isEmpty()) {
            throw new RuntimeException("Environment not provided. Use -Denv=qa/uat/prod");
        }

        switch (env.toLowerCase()) {
            case "qa":
                return getPropValue("qa.url");
            case "uat":
                return getPropValue("uat.url");
            case "prod":
                return getPropValue("prod.url");
            default:
                throw new RuntimeException("Invalid environment: " + env);
        }
    }
}