package com.stravaapi.komwatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SystemProperties {

	private static Properties properties = new Properties();
	
	static {
		try {
			InputStream applicationInput = SystemProperties.class.getClassLoader().getResourceAsStream("application.properties");
			InputStream emailInput = SystemProperties.class.getClassLoader().getResourceAsStream("email.properties");
			properties.load(applicationInput);
			properties.load(emailInput);
			applicationInput.close();
			emailInput.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Properties getProperties() {
		return properties;
	}
	
	public static String getPropertyAsString(String key) {
		return properties.getProperty(key);
	}
	
	public static Integer getPropertyAsInteger(String key) {
		String propertyStr = properties.getProperty(key);
		return Integer.valueOf(propertyStr);
	}
	
	public static boolean getPropertyAsBoolean(String key) {
		String propertyStr = properties.getProperty(key);
		return Boolean.valueOf(propertyStr);
	}
	
	public static void setProperty(String key, String value) {
		
		properties.setProperty(key, value);
	}
	
	public static void setMultipleProperties(Map<String, String> propertiesMap) {
		
		for(Entry<String, String> entry : propertiesMap.entrySet()) {
			properties.setProperty(entry.getKey(), entry.getValue());
		}
	}
	
	public static void setPropertiesFromFile(File propertiesFile) {
		
		try(InputStream in = new FileInputStream(propertiesFile)) {
			
			properties.load(in);
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setMultiplePropertiesInFile(Map<String, String> propertiesMap, File file) {
		OutputStream output = null;
		InputStream input = null;
		try {
			Properties properties = new Properties();
			input = new FileInputStream(file);
			properties.load(input);
			input.close();
			
			output = new FileOutputStream(file);
			for(Entry<String, String> entry : propertiesMap.entrySet()) {
				properties.setProperty(entry.getKey(), entry.getValue());
			}
			properties.store(output, "Adding properties to " + file.getAbsolutePath());
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
