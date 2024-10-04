package com.master.org.excel_master;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Setting {
    private static final Logger LOGGER = Logger.getLogger(Setting.class.getName());
    private List<Settings> settingProperties = new ArrayList<>();
    private String configDirectoryPath;
    private String configFilePath;

    public Setting() {
        this.settingProperties.addAll(List.of(Settings.PATTERN, Settings.NUMBER));
        this.configDirectoryPath = "src/main/resources/config";
        this.configFilePath = getConfigDirectoryPath() + "/config.properties";
    }

    public String getConfigDirectoryPath() {
        return configDirectoryPath;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public List<Settings> getSettingProperties() {
        return settingProperties;
    }

    public void setConfigDirectoryPath(String configDirectoryPath) {
        this.configDirectoryPath = configDirectoryPath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public void setSettingProperties(List<Settings> settingProperties) {
        this.settingProperties = settingProperties;
    }

    public void createConfigFile() {
        // Проверяем наличие папки и создаём её, если она отсутствует
        File configDirectory = new File(getConfigDirectoryPath());
        File configFile = new File(configDirectory, "config.properties");

        if (!configDirectory.exists()) if (configDirectory.mkdirs()) {
            System.out.println("Папка \"config\" успешно создана.");
        } else {
            System.out.println("Не удалось создать папку \"config\".");
            return;
        }

        // Проверяем наличие файла config.properties
        if (configFile.exists()) {
            System.out.println("Файл config.properties уже существует. Создание нового файла не требуется.");
            return; // Если файл существует, не создаём его заново
        }

        saveConfigFile(configFile, settingProperties.stream()
                .map(Settings::getDefaultValue)
                .collect(Collectors.toList()));
    }


    public void editConfigFile(List<String> values) {
        File configDirectory = new File(getConfigDirectoryPath());
        File configFile = new File(configDirectory, "config.properties");
        saveConfigFile(configFile, values);
    }

    public Map<String, String> readConfigFile() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(getConfigFilePath())) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return convertPropertiesToMap(properties);
    }

    private Map<String, String> convertPropertiesToMap(Properties properties) {
        return getSettingProperties().stream().collect(Collectors.toMap(
                Settings::getClassName, settings -> properties.getProperty(settings.getClassName())
        ));
    }

    private void saveConfigFile(File configFile, List<String> values) {
        Properties properties = new Properties();
        List<Settings> settings = getSettingProperties();
        IntStream.range(0, settings.size()).forEach(i -> {
            Settings propertyKey = settings.get(i);
            String value = values.isEmpty() ? propertyKey.getDefaultValue() : values.get(i);
            properties.setProperty(propertyKey.getClassName(), value);
        });

        // Записываем параметры в файл
        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            properties.store(fos, "Config file with default settings");
            LOGGER.info("Конфигурационный файл сохранен.");
        } catch (IOException e) {
            LOGGER.severe("Ошибка записи файла: " + e.getMessage());
        }
    }
}
