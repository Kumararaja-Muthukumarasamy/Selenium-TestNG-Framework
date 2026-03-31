package com.framework.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

	private static final Properties prop = new Properties();

	static {		
		try(InputStream iStream = ConfigReader.class
				.getClassLoader()
				.getResourceAsStream("config.properties")){
			if (iStream == null) {
				throw new RuntimeException("config.properties not found in classpath");
			}

			prop.load(iStream);

		}catch(Exception e) {
			throw new RuntimeException("Failed to load config.properties", e);
		}				
	}

	private ConfigReader() {}

	public static String getPropValue(String key) {

	    String valueFromSystem = System.getProperty(key);

	    if (valueFromSystem != null) {
	        return valueFromSystem;
	    }

	    return prop.getProperty(key);
	}
	
	public static String getEnvironmentUrl() {

	    String env = getPropValue("env");   // qa / uat / prod

	    return switch (env.toLowerCase()) {
	        case "qa" -> getPropValue("qa.url");
	        case "uat" -> getPropValue("uat.url");
	        case "prod" -> getPropValue("prod.url");
	        default -> throw new RuntimeException("Invalid environment: " + env);
	    };
	}
}