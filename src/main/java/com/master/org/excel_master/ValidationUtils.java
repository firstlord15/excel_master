package com.master.org.excel_master;

import java.io.File;
import java.util.Locale;

public class ValidationUtils {
    private FileHandler fileHandler = new FileHandler();

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    // Проверка формата файла
    public boolean validateFileFormat(File file) {
        String fileName = file.getName().toLowerCase(Locale.ROOT);
        return fileName.endsWith(".xlsx") || fileName.endsWith(".xls");
    }

    // Проверка наличия обязательных данных
    public boolean validateRequiredDataPresence () {
        return false;
    }

    // Проверка форматов данных
    public boolean validateDataFormat () {
        return false;
    }

    // Проверка на пустые ячейки
    public boolean validateNonEmptyCells () {
        return false;
    }

    // Проверка соответствия настроек и данных
    public boolean validateSettingsMatchData () {
        return false;
    }

    // Проверка уникальности
    public boolean validateUniqueEntries () {
        return false;
    }

    // Логика валидации значений
    public boolean validateValueRanges  () {
        return false;
    }
}
