package com.master.org.excel_master.backend;

import com.master.org.excel_master.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;

import static com.master.org.excel_master.AlertUtils.showInfoAlert;

public class Controller {
    private final String CasualDelivery = "Обычная поставка";
    private final String QrDelivery = "QR поставка";

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        startButton.setOnAction(event -> handleStartButtonClick());
        comboBox.getItems().addAll(CasualDelivery, QrDelivery);
    }

    // Метод выбора типа функции
    private void handleStartButtonClick() {
        String selectedOption = comboBox.getValue();
        if (CasualDelivery.equals(selectedOption)) {
            FileHandler fh = new FileHandler();
            Stage stage = (Stage) startButton.getScene().getWindow();
            File ourFile = fh.openFileChooser(stage);
        } else if (QrDelivery.equals(selectedOption)) {
            QrDeliveryMethod();
        } else {
            showInfoAlert("Предупреждение", "Ай Ай Ай, не так быстро, сначала выбери тип поставки.");
        }
    }

    private void QrDeliveryMethod() {
        System.out.println("Выбрана QR поставка, действие не требуется");
    }
}
