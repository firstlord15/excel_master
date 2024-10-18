package com.master.org.excel_master.utils;

import com.master.org.excel_master.services.WorkbookCreationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WorkbookBuilder {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private XSSFWorkbook workbook;
    private File lastUsedFile;

    // Метод для создания Workbook из файла
    public XSSFWorkbook buildWorkbook(File file) throws WorkbookCreationException {
        if (workbook != null && file.equals(lastUsedFile)) {
            LOGGER.info("Reusing cached workbook for file: {}", file.getName());
            return workbook;
        }

        LOGGER.info("Building workbook from file: {}", file.getName());
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = new XSSFWorkbook(fis);
            lastUsedFile = file;
        } catch (IOException e) {
            LOGGER.error("Failed to create workbook from file: {}", file.getName(), e);
            throw new WorkbookCreationException("Error creating workbook from file: " + file.getName(), e);
        }
        return workbook;
    }

    // Метод для создания Workbook из InputStream (расширяемость)
    public XSSFWorkbook buildWorkbook(InputStream inputStream) throws WorkbookCreationException {
        LOGGER.info("Building workbook from InputStream");
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to create workbook from InputStream", e);
            throw new WorkbookCreationException("Error creating workbook from InputStream", e);
        }
        return workbook;
    }
}