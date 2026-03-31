package com.framework.utils;

import java.io.InputStream;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConfigReader {

    private static ConfigReader instance;
    private Properties prop;
    private Dotenv dotenv;

    private ConfigReader() {
        try (InputStream is = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            prop = new Properties();
            prop.load(is);
            dotenv = Dotenv.configure().ignoreIfMissing().load();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config");
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getProperty(String key) {
    	
    	// 1. System property highest priority
    	String value = System.getProperty(key);
    	
    	// 2. .env file (local)
        if (value == null && dotenv != null) {
            value = dotenv.get(key);
        }

        // 3. Environment variable (CI)
    	if(value == null) {
    		value = System.getenv(key);
    	}    	    	
        
        // 4. properties file (fallback)
        if (value == null) {
            value = prop.getProperty(key);
        }
        return value;
    }
}