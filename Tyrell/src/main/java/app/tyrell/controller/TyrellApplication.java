package app.tyrell.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class TyrellApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(appController.class.getResource("NewAPP.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.getIcons().add(new Image("file:src/main/resources/Icon/logo.png"));
        stage.setResizable(false);
        stage.setTitle("Tyrell Manager");
        stage.setScene(scene);
        stage.show();
    }
}