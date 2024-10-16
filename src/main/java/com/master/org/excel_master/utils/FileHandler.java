package com.master.org.excel_master.utils;

import com.master.org.excel_master.services.DataProcessor;
import com.master.org.excel_master.services.ExcelEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileHandler {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);

    public void chooseAndProcessFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

        // Выбор основного файла
        File selectedMainFile = fileChooser.showOpenDialog(stage);
        if (selectedMainFile == null) {
            LOGGER.warn("No main file selected.");
            return;
        }
        LOGGER.info("Selected main file: {}", selectedMainFile.getName());
        LOGGER.info("Main file path: {}", selectedMainFile.getAbsolutePath());

        // Выбор вторичного файла
        File selectedSecondaryFile = fileChooser.showOpenDialog(stage);
        if (selectedSecondaryFile == null) {
            LOGGER.warn("No secondary file selected.");
            return;
        }
        LOGGER.info("Selected secondary file: {}", selectedSecondaryFile.getName());
        LOGGER.info("Secondary file path: {}", selectedSecondaryFile.getAbsolutePath());

        // Обработка данных
        DataProcessor dataProcessor = new DataProcessor();
        ExcelEditor excelEditor = new ExcelEditor();
        dataProcessor.setMainFile(selectedMainFile);
        dataProcessor.setSecondaryFile(selectedSecondaryFile);

        try {
            excelEditor.writeDataToFile(dataProcessor.getData(), dataProcessor.getShkBoxes(), selectedMainFile);
            LOGGER.info("Data written to file successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing files: {}", e.getMessage(), e);
        }
    }
}
