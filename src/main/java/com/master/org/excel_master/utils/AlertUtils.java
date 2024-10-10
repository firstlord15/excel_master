package com.master.org.excel_master.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

    // Метод для вывода информационного сообщения
    public static void showInfoAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);  // Заголовок можно не использовать
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Метод для вывода предупреждения
    public static void showWarningAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Метод для вывода сообщения об ошибке
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Метод для подтверждения действия (да/нет)
    public static boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Ждем ответа пользователя
        return alert.showAndWait().isPresent();  // Возвращает true, если пользователь нажал OK
    }
}
