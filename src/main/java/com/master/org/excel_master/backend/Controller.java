package com.master.org.excel_master.backend;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class Controller {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void initialize() {
        // Заполняем ComboBox данными
        comboBox.getItems().addAll(
            "Обычная поставка",
            "QR поставка"
        );

        comboBox.setValue("Обычная поставка");

        
    }
}
