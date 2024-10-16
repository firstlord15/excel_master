package com.master.org.excel_master.services;

import com.master.org.excel_master.models.RowData;
import com.master.org.excel_master.utils.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelEditor {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);

    public void writeDataToFile(List<RowData> rowDataList, List<String> shkBoxes, File file) {
        String filePath = file.getParent() + "\\result.xlsx";
        LOGGER.info("Start writing data to the file excel: {}", filePath);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Запись заголовков (если необходимо)
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("баркод товара");
        headerRow.createCell(1).setCellValue("кол-во товаров");
        headerRow.createCell(2).setCellValue("шк короба");
        headerRow.createCell(3).setCellValue("срок годности");

        // Запись данных в файл
        for (int i = 0; i < rowDataList.size(); i++) {
            RowData rowData = rowDataList.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(rowData.getBarcode());
            row.createCell(1).setCellValue(rowData.getCountItems());

            int countBoxesIndex = (int) rowData.getCountBoxes();
            LOGGER.info("index: " + (countBoxesIndex-1));
            if (countBoxesIndex < shkBoxes.size()) {
                row.createCell(2).setCellValue(shkBoxes.get(countBoxesIndex-1));
            } else {
                LOGGER.warn("Index out of bounds for shkBoxes: {}", countBoxesIndex-1);
                row.createCell(2).setCellValue("N/A");
            }
        }

        // Сохранение файла
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            LOGGER.info("Data successfully written to file: {}", filePath);
        } catch (IOException e) {
            LOGGER.error("Error writing to a file: {}", e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                LOGGER.error("File close error: {}", e.getMessage());
            }
        }
    }
}
