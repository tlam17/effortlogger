package com.effortlogger;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

 
public class EffortLogger extends Application { 
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) throws IOException {
    		Parent root = FXMLLoader.load(getClass().getResource("/fxml/console.fxml"));
        	Scene scene = new Scene(root, 600, 400);
        	primaryStage.setTitle("EffortLogger");
        	primaryStage.setScene(scene);
        	primaryStage.show();
    }
}