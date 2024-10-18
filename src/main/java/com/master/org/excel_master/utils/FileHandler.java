package com.master.org.excel_master.utils;

import com.master.org.excel_master.services.DataProcessor;
import com.master.org.excel_master.services.FileExportService;
import com.master.org.excel_master.services.PathService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileHandler {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private final PathService pathService = new PathService();
    String path = pathService.getPath();


    public void chooseAndProcessFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();

        if (!path.isEmpty()) fileChooser.setInitialDirectory(new File(path));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

        // Выбор первого файла
        File selectedFirstFile = fileChooser.showOpenDialog(stage);
        if (selectedFirstFile == null) {
            LOGGER.warn("No main file selected.");
            return;
        }

        LOGGER.info("Selected first file: {}", selectedFirstFile.getName());
        LOGGER.info("Main file path: {}", selectedFirstFile.getAbsolutePath());

        // Обновление пути
        pathService.setPath(selectedFirstFile.getParent());

        // Выбор второго файла
        path = pathService.getPath();
        if (!path.isEmpty()) fileChooser.setInitialDirectory(new File(path));

        File selectedSecondFile = fileChooser.showOpenDialog(stage);
        if (selectedSecondFile == null) {
            LOGGER.warn("No secondary file selected.");
            return;
        }
        LOGGER.info("Selected second file: {}", selectedSecondFile.getName());
        LOGGER.info("Secondary file path: {}", selectedSecondFile.getAbsolutePath());

        // Обработка данных
        DataProcessor dataProcessor = new DataProcessor();
        FileExportService fileExportService = new FileExportService();

        // Проверка правильности выбора файлов
        if (selectedSecondFile.getName().startsWith("template") || selectedFirstFile.getName().startsWith("shk")){
            dataProcessor.setMainFile(selectedSecondFile);
            dataProcessor.setSecondaryFile(selectedFirstFile);
        } else {
            dataProcessor.setMainFile(selectedFirstFile);
            dataProcessor.setSecondaryFile(selectedSecondFile);
        }

        try {
            fileExportService.writeDataToFile(dataProcessor.getData(), dataProcessor.getShkBoxes(), selectedFirstFile);
            LOGGER.info("Data written to file successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing files: {}", e.getMessage(), e);
        }
    }
}
