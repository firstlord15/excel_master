package com.master.org.excel_master.services;

import com.master.org.excel_master.models.RowData;
import com.master.org.excel_master.utils.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private File mainFile;
    private File secondaryFile;

    public DataProcessor() {}

    public File getMainFile() {
        return mainFile;
    }

    public void setMainFile(File mainFile) {
        this.mainFile = mainFile;
    }

    public File getSecondaryFile() {
        return secondaryFile;
    }

    public void setSecondaryFile(File secondaryFile) {
        this.secondaryFile = secondaryFile;
    }

    private void processCell(XSSFCell cell, RowData rowData, int colIndex, double[] countBoxes) {
        switch (colIndex) {
            case 0 -> rowData.processBarcodeCell(cell);
            case 1 -> rowData.processCountItemsCell(cell);
            case 4 -> rowData.processCountBoxesCell(cell, countBoxes);
        }
    }

    private RowData mainProcessRow(XSSFRow row, double[] countBoxes) {
        RowData rowData = new RowData();
        for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
            XSSFCell cell = row.getCell(colIndex);
            if (cell != null) {
                processCell(cell, rowData, colIndex, countBoxes);
            }
        }
        rowData.setCountBoxes(countBoxes[0]);
        return rowData;
    }

    private List<RowData> getDataCells(XSSFSheet sheet) {
        LOGGER.info("Extracting data cells from sheet...");
        List<RowData> data = new ArrayList<>();
        double[] countBoxes = new double[1];

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                data.add(mainProcessRow(row, countBoxes));
            } else {
                LOGGER.warn("Null row found at index {}.", rowIndex);
            }
        }
        LOGGER.info("Row data added: {}", data.size());
        return data;
    }

    private List<RowData> getCells(File file) {
        LOGGER.info("Reading file: {}", file.getName());
        try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            LOGGER.info("Sheet processed: {}", sheet.getSheetName());
            return getDataCells(sheet);
        } catch (IOException e) {
            LOGGER.error("Error reading file {}: {}", file.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<String> getDataShkBoxFromFile(File file) {
        LOGGER.info("Reading file: {}", file.getName());
        List<String> rowData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (row != null) {
                    XSSFCell cell = row.getCell(2); // Получаем ячейку из столбца с индексом 2
                    if (cell != null) {
                        rowData.add(cell.toString());
                    }
                }
            }
            LOGGER.info("Row data extracted successfully");
        } catch (IOException e) {
            LOGGER.error("Error reading file {}: {}", file.getName(), e.getMessage());
        }
        return rowData;
    }

    public List<RowData> getData() {
        return getCells(getMainFile());
    }

    public List<String> getShkBoxes() {
        return getDataShkBoxFromFile(getSecondaryFile());
    }

    public void toMainConsole() {
        getData().forEach(System.out::println);
    }

    public void toSHKConsole() {
        getShkBoxes().forEach(System.out::println);
    }
}
