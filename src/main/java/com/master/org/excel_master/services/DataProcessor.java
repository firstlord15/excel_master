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
    private File file;

    public DataProcessor() {}

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    private void processCell(XSSFCell cell, RowData rowData, int colIndex, double[] countBoxes) {
        switch (colIndex) {
            case 0 -> rowData.processBarcodeCell(cell);
            case 1 -> rowData.processCountItemsCell(cell);
            case 4 -> rowData.processCountBoxesCell(cell, countBoxes);
        }
    }

    private RowData processRow(XSSFRow row, double[] countBoxes) {
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
                RowData rowData = processRow(row, countBoxes);
                data.add(rowData);
            } else LOGGER.atInfo().log("Null row found at index %d.".formatted(rowIndex));
        }
        LOGGER.atInfo().log("Row data added x%d.".formatted(data.size()));
        LOGGER.info("Data cell extraction completed.");
        return data;
    }

    public List<RowData> getCells(File file) {
        LOGGER.atInfo().log("Reading file: %s".formatted(file.getName()));
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            LOGGER.atInfo().log("Sheet processed: %s".formatted(sheet.getSheetName()));
            return getDataCells(sheet);
        } catch (IOException e) {
            LOGGER.atError().log("Error reading file: %s".formatted(e));
            throw new RuntimeException(e);
        }
    }

    public List<RowData> getData() {
        return getCells(getFile());
    }

    public void toConsole() {
        List<RowData> cells = getCells(getFile());
        cells.stream().map(RowData::toString).forEach(System.out::println);
    }
}

