package com.effortlogger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class Editor implements Initializable {

	@FXML
	private ChoiceBox<String> projectChoice;
	@FXML
	private ChoiceBox<String> categoryChoice;
	@FXML
	private ChoiceBox<String> lifeCycleChoice;
	@FXML
	private ChoiceBox<String> logChoice;
	//dummy data

	private String[] projects = {"ProjectDemo1", "ProjectDemo2"};

	private String[] categories = {"CategoryDemo1", "CategoryDemo2"};

	private String[] lifeCycles = {"lifeCycleDemo1", "lifeCycleDemo2"};

	private String[] logs = {"logDemo1", "logDemo2"};
	//clears all the user inputs 
	public void clearEntry(ActionEvent event) {
		projectChoice.setValue("");
		categoryChoice.setValue("");
		lifeCycleChoice.setValue("");
		logChoice.setValue("");
	}
	@Override
	//this method is for getting all the data to be in our choice box
	public void initialize(URL arg0, ResourceBundle arg1) {
		projectChoice.getItems().addAll(projects);
		categoryChoice.getItems().addAll(categories);
		lifeCycleChoice.getItems().addAll(lifeCycles);
		logChoice.getItems().addAll(logs);
	}
}
