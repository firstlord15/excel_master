package com.master.org.excel_master.models;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class RowData {
    private double barcode;
    private double countItems;
    private double countBoxes;

    public RowData() {}
    public RowData(double barcode, double countItems, double countBoxes) {
        this.barcode = barcode;
        this.countItems = countItems;
        this.countBoxes = countBoxes;
    }

    public void processBarcodeCell(XSSFCell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> setBarcode(cell.getNumericCellValue());
                case STRING -> setBarcode(Double.parseDouble(cell.getStringCellValue()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Невозможно преобразовать строку в число: " + cell.getStringCellValue());
        }
    }

    public void processCountItemsCell(XSSFCell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            setCountItems(cell.getNumericCellValue());
        } else {
            setCountItems(-1); // Значение по умолчанию, если данные некорректны
        }
    }

    public void processCountBoxesCell(XSSFCell cell, double[] countBoxes) {
        if (cell.getCellType() == CellType.NUMERIC) {
            countBoxes[0] = cell.getNumericCellValue();
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
