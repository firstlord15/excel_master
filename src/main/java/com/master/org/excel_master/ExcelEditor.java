package com.master.org.excel_master;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelEditor {
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

    public void newMain() {
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            // Проходимся по всем строкам и столбцам
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                var row = sheet.getRow(rowIndex);
                if (row != null) {
                    for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
                        var cell = row.getCell(colIndex);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING -> System.out.print(cell.getStringCellValue() + "\t");
                                case NUMERIC -> System.out.print(cell.getNumericCellValue() + "\t");
                                case BOOLEAN -> System.out.print(cell.getBooleanCellValue() + "\t");
                                default -> System.out.print("Unknown Type\t");
                            }
                        }
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
