package com.master.org.excel_master.services;

import com.master.org.excel_master.models.RowData;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelEditor {
    private static final Logger LOGGER = Logger.getLogger(ExcelEditor.class.getName());
    private File file = new File("src/main/resources/file.xlsx");

//    public ExcelEditor(File file) {
//        this.file = file;
//    }

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
        LOGGER.info("Извлечение ячеек с данными из листа...");
        List<RowData> data = new ArrayList<>();
        double[] countBoxes = new double[1];
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                RowData rowData = processRow(row, countBoxes);
                data.add(rowData);
                LOGGER.log(Level.FINE, "Добавлены данные строки для индекса строки {0}.", rowIndex);
            } else LOGGER.log(Level.WARNING, "В индексе {0} обнаружена нулевая строка.", rowIndex);
        }
        LOGGER.info("Завершено извлечение ячеек данных.");
        return data;
    }

    public List<RowData> getCells(File file) {
        LOGGER.log(Level.INFO, "Чтения файла: {0}", file.getName());
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            LOGGER.log(Level.INFO, "Лист обработан: {0}", sheet.getSheetName());
            return getDataCells(sheet);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка в чтении файла: ", e);
            throw new RuntimeException(e);
        }
    }

    public void toConsole() {
        LOGGER.info("Начало обработки данных...");
        List<RowData> cells = getCells(getFile());
        cells.stream().map(RowData::toString).forEach(System.out::println);
        LOGGER.info("Обработка данных завершена.");
    }
}
