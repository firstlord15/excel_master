package com.master.org.excel_master.services;

import com.master.org.excel_master.utils.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PathService {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private static final String CONFIG_FILE_PATH = "src/main/resources/config/config.properties";
    private static final String DEFAULT_PATH = "";
    private static final String FILE_PATH_KEY = "file.path";

    public String getPath() {
        LOGGER.info("Retrieving file path from config.properties...");
        Properties properties = new Properties();
        String path = DEFAULT_PATH;

        if (!configFileExists()) {
            LOGGER.warn("Configuration file not found. Creating default config...");
            setPath(DEFAULT_PATH);
        }

        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            path = properties.getProperty(FILE_PATH_KEY, DEFAULT_PATH);
            LOGGER.info("File path successfully retrieved: {}", path);
        } catch (IOException e) {
            LOGGER.error("Error reading config.properties: {}", e.getMessage(), e);
        }

        if (path.isEmpty()) {
            LOGGER.warn("The file path in config.properties is either missing or empty.");
        }
        return path;
    }

    public void setPath(String newPath) {
        LOGGER.debug("Updating 'file.path' in config.properties...");
        Properties properties = new Properties();

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.setProperty(FILE_PATH_KEY, newPath);
            properties.store(output, "Updated 'file.path' value");
            LOGGER.info("Successfully updated 'file.path' to: {}", newPath);
        } catch (IOException e) {
            LOGGER.error("Failed to update 'file.path' in config.properties. Error: {}", e.getMessage(), e);
        }
    }

    private boolean configFileExists() {
        File configFile = new File(CONFIG_FILE_PATH);
        return configFile.exists() && configFile.isFile();
    }
}

