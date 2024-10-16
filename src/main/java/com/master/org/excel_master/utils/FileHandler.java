package com.master.org.excel_master.utils;

import com.master.org.excel_master.services.DataProcessor;
import com.master.org.excel_master.services.ExcelEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.MessageFormat;
public class FileHandler {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    public void chooseAndProcessFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            LOGGER.info(MessageFormat.format("Selected file: {0}", selectedFile.getName()));
            LOGGER.info(MessageFormat.format("Path: {0}", selectedFile.getAbsolutePath()));
            DataProcessor dataProcessor = new DataProcessor();
            ExcelEditor excelEditor = new ExcelEditor();
            dataProcessor.setFile(selectedFile);
            try {
//                dataProcessor.toConsole();
                excelEditor.writeDataToFile(dataProcessor.getData(), selectedFile);
            } catch (Exception e) {
                LOGGER.error("Error processing file: " + e.getMessage(), e);
            }
        } else {
            LOGGER.warn("File not selected");
        }
    }
}
