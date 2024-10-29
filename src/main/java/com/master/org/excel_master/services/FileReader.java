package com.master.org.excel_master.services;

import com.master.org.excel_master.models.RowData;
import com.master.org.excel_master.utils.FileHandler;
import com.master.org.excel_master.utils.WorkbookBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);

    public List<RowData> readExcelFile(File file) {
        LOGGER.info("Reading file: {}", file.getName());
        try (FileInputStream ignored = new FileInputStream(file); XSSFWorkbook workbook = new WorkbookBuilder().buildWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            LOGGER.debug("Sheet processed: {}", sheet.getSheetName());
            return extractDataFromSheet(sheet);
        } catch (WorkbookCreationException e) {
            LOGGER.error("Error creating workbook for file {}: {}", file.getName(), e.getMessage());
            throw new RuntimeException("Failed to process Excel file: " + file.getName(), e);
        } catch (IOException e) {
            LOGGER.error("IO error while reading file {}: {}", file.getName(), e.getMessage());
            throw new RuntimeException("IO error while processing Excel file: " + file.getName(), e);
        }
    }

    public List<String> getDataShkBoxFromFile(File file) {
        LOGGER.debug("Reading file: {}", file.getName());
        List<String> rowData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (row != null) {
                    XSSFCell cell = row.getCell(2);
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

    private List<RowData> extractDataFromSheet(XSSFSheet sheet) {
        LOGGER.info("Extracting data from sheet...");
        List<RowData> data = new ArrayList<>();
        double difference = sheet.getRow(1).getCell(4).getNumericCellValue();
        var countBoxes = new double[1];
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                if (row.getPhysicalNumberOfCells() >= 3){
                    RowData rowData = new RowData();
                    for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
                        XSSFCell cell = row.getCell(colIndex);
                        if (cell != null) {
                            CellType cellType = cell.getCellType();
                            if (cellType == CellType.STRING || cellType == CellType.NUMERIC) {
                                switch (colIndex) {
                                    case 0 -> rowData.processBarcodeCell(cell);
                                    case 1 -> rowData.processCountItemsCell(cell);
                                    case 4 -> rowData.processCountBoxesCell(cell, countBoxes, difference);
                                }
                            }
                        }
                    }
                    rowData.setCountBoxes(countBoxes[0]);
                    data.add(rowData);
                }
            }
        }
        LOGGER.debug("Row data added: {}", data.size());
        return data;
    }
}
