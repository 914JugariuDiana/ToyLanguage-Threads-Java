package com.example.aa7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("RunProgram.fxml"));
        Parent mainWindow = mainLoader.load();

        RunProgram runProgram = mainLoader.getController();

        stage.setTitle("Main Window");
        stage.setScene(new Scene(mainWindow));
        stage.show();


        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent secondaryWindow = secondaryLoader.load();
        SelectProgram selectWindowController = secondaryLoader.getController();
        selectWindowController.setRunProgramController(runProgram);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Window");
        secondaryStage.setScene(new Scene(secondaryWindow));
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}