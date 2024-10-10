package com.master.org.excel_master;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // load files
            Image icon = new Image("/img/icon.jpg"); // путь к иконке
            URL xmlUrl = getClass().getResource("/view/Fnew.fxml");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            
            // Scence
            Scene scene = new Scene(root);
            scene.getStylesheets().add("view/css/fruit.css");
            
            // Primary stage setting
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(600);
            primaryStage.setTitle("Excel Master");
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
