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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileExportService {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM_HHmmss");

    public void writeDataToFile(List<RowData> rowDataList, List<String> shkBoxes, File file) {
        String fileName = "\\"+"Export_" + LocalDateTime.now().format(formatter)+".xlsx";
        String filePath = file.getParent() + fileName;
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
            if (countBoxesIndex > 0 && countBoxesIndex <= shkBoxes.size()) {
                row.createCell(2).setCellValue(shkBoxes.get(countBoxesIndex - 1));
            } else {
                LOGGER.warn("Invalid countBoxesIndex: {}. It should be between 1 and {}", countBoxesIndex, shkBoxes.size());
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
