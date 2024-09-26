package com.master.org.excel_master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Setting {
    private List<String> settingProperties = List.of("Pattern", "Numbers");
    private String configDirectoryPath = "src/main/resources/config";
    private String configFilePath = getConfigDirectoryPath() + "/config.properties";

    public String getConfigDirectoryPath() {
        return configDirectoryPath;
    }

    public void setConfigDirectoryPath(String configDirectoryPath) {
        this.configDirectoryPath = configDirectoryPath;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public List<String> getSettingProperties() {
        return settingProperties;
    }

    public void setSettingProperties(List<String> settingProperties) {
        this.settingProperties = settingProperties;
    }

    public void readConfigFile() {
        Map<String, String> mapProperties;
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(getConfigFilePath())) {
            properties.load(fis);
            mapProperties = getSettingProperties()
                    .stream()
                    .collect(Collectors.toMap(propertyKey -> propertyKey, properties::getProperty, (a, b) -> b));

            System.out.println(mapProperties.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    public void createConfigFile() {
        // Проверяем наличие папки и создаём её, если она отсутствует
        File configDirectory = new File(getConfigDirectoryPath());
        File configFile = new File(configDirectory, "config.properties");
        if (!configDirectory.exists()) {
            if (configDirectory.mkdir())
                System.out.println("Папка \"config\" успешно создана.");
            else {
                System.out.println("Не удалось создать папку \"config\".");
                return;
            }
        }

        // Проверяем наличие файла config.properties
        if (configFile.exists()) {
            System.out.println("Файл config.properties уже существует. Создание нового файла не требуется.");
            return; // Если файл существует, не создаём его заново
        }

        Properties properties = new Properties();
        for (String propertyKey : getSettingProperties()) {
            properties.setProperty(propertyKey, "");
        }

        // Записываем параметры в файл
        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            properties.store(fos, "Config file with default settings");
            System.out.println("Файл config.properties успешно создан в папке \"config\".");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
