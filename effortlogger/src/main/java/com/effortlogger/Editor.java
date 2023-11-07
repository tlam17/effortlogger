package com.effortlogger;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Editor {
	
	public void clear(ActionEvent e) {
		
	}
	
	public void update(ActionEvent e) {
		
	}
	
	public void delete(ActionEvent e) {
		
	}
	
	public void split(ActionEvent e) {
		
	}
	
	public void effortLoggerConsole(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/fxml/console.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 600, 400);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
}
