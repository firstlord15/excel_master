package com.master.org.excel_master.models;

import com.master.org.excel_master.utils.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class RowData {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private double barcode;
    private double countItems;
    private double countBoxes;

    public RowData() {}

    public void processBarcodeCell(XSSFCell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> setBarcode(cell.getNumericCellValue());
                case STRING -> setBarcode(Double.parseDouble(cell.getStringCellValue()));
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Unable to convert a string to a number: {}", cell.getStringCellValue());
        }
    }

    public void processCountItemsCell(XSSFCell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            setCountItems(cell.getNumericCellValue());
        } else {
            LOGGER.warn("Default value is specified -1");
            setCountItems(-1); // Значение по умолчанию, если данные некорректны
        }
    }

    public void processCountBoxesCell(XSSFCell cell, double[] countBoxes, double difference) {
        if (cell.getCellType() == CellType.NUMERIC) {
            countBoxes[0] = cell.getNumericCellValue() - difference + 1;
        }
    }

    public double getBarcode() {
        return barcode;
    }

    public void setBarcode(double barcode) {
        this.barcode = barcode;
    }

    public double getCountItems() {
        return countItems;
    }

    public void setCountItems(double countItems) {
        this.countItems = countItems;
    }

    public double getCountBoxes() {
        return countBoxes;
    }

    public void setCountBoxes(double countBoxes) {
        this.countBoxes = countBoxes;
    }

    @Override
    public String toString(){
        return "barcode: %s\ncountItems: %s\ncountBoxes: %s\n"
                .formatted(barcode, countItems, countBoxes);
    }
}
