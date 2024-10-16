package com.master.org.excel_master.controllers;

import com.master.org.excel_master.utils.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

import static com.master.org.excel_master.utils.AlertUtils.showInfoAlert;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private final String CasualDelivery = "Обычная поставка";
    private final String QrDelivery = "QR поставка";

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        LOGGER.atInfo().log("Controller initialized");
        startButton.setOnAction(event -> handleStartButtonClick());
        comboBox.getItems().addAll(CasualDelivery, QrDelivery);
        LOGGER.atInfo().log(MessageFormat.format("ComboBox items added: {0} and {1}", CasualDelivery, QrDelivery));
    }

    // Метод выбора типа функции
    private void handleStartButtonClick() {
        String selectedOption = comboBox.getValue();
        if (CasualDelivery.equals(selectedOption)) {
            LOGGER.atInfo().log("Casual Delivery selected");
            casualDeliveryMethod();
        } else if (QrDelivery.equals(selectedOption)) {
            LOGGER.atInfo().log("QR Delivery selected");
            qrDeliveryMethod();
        } else {
            LOGGER.atInfo().log("No delivery type selected");
            showInfoAlert("Warning", "Hold on, choose a delivery type first.");
        }
    }

    private void casualDeliveryMethod() {
        FileHandler fh = new FileHandler();
        Stage stage = (Stage) startButton.getScene().getWindow();
        LOGGER.atInfo().log("Starting file processing in casual delivery mode");
        fh.chooseAndProcessFile(stage);
    }
    private void qrDeliveryMethod() {
        LOGGER.atInfo().log("QR Delivery selected, no further action required");
    }
}
