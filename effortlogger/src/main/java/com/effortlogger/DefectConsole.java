package com.effortlogger;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DefectConsole {
	
	public void clear(ActionEvent e) {
		
	}
	
	public void newDefect(ActionEvent e) {
		
	}
	
	public void close(ActionEvent e) {
		
	}
	
	public void reopen(ActionEvent e) {
		
	}
	
	public void delete(ActionEvent e) {
		
	}
	
	public void update(ActionEvent e) {
		
	}
	
	public void effortLoggerConsole(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("console.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 600, 400);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
}
