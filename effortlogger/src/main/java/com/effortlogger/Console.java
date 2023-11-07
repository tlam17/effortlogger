package com.effortlogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Console {
	
	// Formatting for the date and time
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static LocalDateTime startTime;
	private static LocalDateTime endTime;
		
	// Lists to populate choice boxes
	private static ObservableList<String> projects = FXCollections.observableArrayList();
	private static ObservableList<String> lifeCycleSteps = FXCollections.observableArrayList();
	private static ObservableList<String> effortCategories = FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects");
	private static ObservableList<String> plansList = FXCollections.observableArrayList();
	private static ObservableList<String> deliverablesList = FXCollections.observableArrayList();
	private static ObservableList<String> interruptionsList = FXCollections.observableArrayList();
	private static ObservableList<String> defectsList = FXCollections.observableArrayList();
	
	// FXML IDs
	@FXML
	private Rectangle rectangle;
	@FXML
	private Text clockStatus;
	@FXML
	private Text subordinateText;
	@FXML
	private ChoiceBox<String> project;
	@FXML
	private ChoiceBox<String> lifeCycleStep;
	@FXML
	private ChoiceBox<String> effortCategory;
	@FXML
	private ChoiceBox<String> subordinates;
	
	public void initialize() throws IOException {
		readDefinitions();
		// Initialize ChoiceBoxes
		project.setItems(projects);
		lifeCycleStep.setItems(lifeCycleSteps);
		effortCategory.setItems(effortCategories);
		// Changes subordinate section based on effort category section
		effortCategory.setOnAction(event -> {
			if (effortCategory.getValue().equals("Plans")) {
				subordinateText.setText("Plans:");
				subordinates.setItems(plansList);
			} else if (effortCategory.getValue().equals("Deliverables")) {
				subordinateText.setText("Deliverables:");
				subordinates.setItems(deliverablesList);
			} else if (effortCategory.getValue().equals("Interruptions")) {
				subordinateText.setText("Interruptions:");
				subordinates.setItems(interruptionsList);
			} else if (effortCategory.getValue().equals("Defects")) {
				subordinateText.setText("Defects:");
				subordinates.setItems(defectsList);
			}
		});
	}
	
	// Starts the timer
	public void start(ActionEvent e) {
		rectangle.setFill(Color.LIGHTGREEN);
		clockStatus.setText("Clock is running");
		// Get current time and date
		startTime = LocalDateTime.now();
		System.out.println(startTime);
	}
	
	// Stops the timer
	public void stop(ActionEvent e) {
		rectangle.setFill(Color.RED);
		clockStatus.setText("Clock is stopped");
		// Get current time and date
		endTime = LocalDateTime.now();
		Duration delta = calculateDeltaTime(startTime, endTime);
		String date = startTime.format(dtf).substring(0, 10);
		String starttime_formatted = startTime.format(dtf).substring(11);
		String endtime_formatted = endTime.format(dtf).substring(11);
		String lifeCycleStep_ = lifeCycleStep.getValue();
		String effortCategory_ = effortCategory.getValue();
		String subordinate_ = subordinates.getValue();
		System.out.println(endTime);
	}
	
	// Moves to the editor
	public void effortLogEditor(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/fxml/editor.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 600, 400);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
	
	// Moves to the defect log console
	public void defectLogConsole(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/fxml/defectconsole.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 800, 600);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
	
	public void definitions(ActionEvent e) {
		
	}
	
	public void logs(ActionEvent e) {
		
	}
	
	public static Duration calculateDeltaTime(LocalDateTime startTime, LocalDateTime stopTime) {
        return Duration.between(startTime, stopTime);
    }
	
	public static void readDefinitions() throws IOException {
			FileInputStream file = new FileInputStream(new File("src/main/resources/EffortLoggerData.xlsm"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(1);
			// Populate choice boxes
	        populateList(CellRangeAddress.valueOf("D7:D16"), projects, sheet);
	        populateList(CellRangeAddress.valueOf("D20:D50"), lifeCycleSteps, sheet);
	        populateList(CellRangeAddress.valueOf("N39:N48"), deliverablesList, sheet);
	        populateList(CellRangeAddress.valueOf("N27:N36"), plansList, sheet);
	        populateList(CellRangeAddress.valueOf("N51:N60"), interruptionsList, sheet);
	        populateList(CellRangeAddress.valueOf("N63:N77"), defectsList, sheet);
			workbook.close();
	}
	
	public static void populateList(CellRangeAddress range, ObservableList<String> list, XSSFSheet sheet) {
		int firstRow = range.getFirstRow();
        int lastRow = range.getLastRow();
        int column = range.getFirstColumn();
        for (int rowNum = firstRow; rowNum <= lastRow; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            Cell cell = row.getCell(column);
            if (cell == null) {
                continue;
            }
            if (!cell.toString().equals("")) {
            	list.add(cell.toString());
            }
        }

	}
	
	public static void enterLog() {
		
	}
}
