package com.master.org.excel_master;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileHandler {
    // Метод для открытия выбора файла
    public File openFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null){
            System.out.println("Выбран файл: " + selectedFile.getName());
            System.out.println("Путь: " + selectedFile.getAbsolutePath());
        }
        else System.out.println("Файл не выбран");

        return selectedFile;
    }



}
