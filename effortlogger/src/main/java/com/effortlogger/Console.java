package com.effortlogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.Duration;
import java.time.LocalDate;
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
	
	//Lists to pass into Logs Class
	private static ObservableList<String> logsNumber = FXCollections.observableArrayList();
	private static ObservableList<String> logsDate = FXCollections.observableArrayList();
	private static ObservableList<String> logsStart = FXCollections.observableArrayList();
	private static ObservableList<String> logsStop = FXCollections.observableArrayList();
	private static ObservableList<String> logsTime = FXCollections.observableArrayList();
	private static ObservableList<String> logsLifeCycle = FXCollections.observableArrayList();
	private static ObservableList<String> logsCategory = FXCollections.observableArrayList();
	private static ObservableList<String> logsDeliverable = FXCollections.observableArrayList();



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
		readLogs();
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
	public void stop(ActionEvent e) throws IOException {
		rectangle.setFill(Color.RED);
		clockStatus.setText("Clock is stopped");
		// Get current time and date
		endTime = LocalDateTime.now();
		Duration delta = calculateDeltaTime(startTime, endTime);
		String lifeCycleStep_ = lifeCycleStep.getValue();
		String effortCategory_ = effortCategory.getValue();
		String subordinate_ = subordinates.getValue();
		System.out.println(endTime);
		enterLog();
	}
	
	// Moves to the editor
	public void effortLogEditor(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/editor.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 600, 400);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
	
	// Moves to the defect log console
	public void defectLogConsole(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/defectconsole.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 800, 600);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
	
	public void definitions(ActionEvent e) {
		
	}
	public void logs(ActionEvent e) throws IOException{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/logs.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 800, 650);
    	stage.setTitle("EffortLogger");
    	stage.setScene(scene);
    	stage.show();
	}
	
	public static void readLogs() throws IOException {
			FileInputStream file = new FileInputStream(new File("effortlogger\\effortlogger\\src\\main\\resources\\EffortLoggerData.xlsm"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			logsNumber.clear();
			logsDate.clear();
			logsStart.clear();
			logsStop.clear();
			logsTime.clear();
			logsCategory.clear();
			logsLifeCycle.clear();
			logsDeliverable.clear();
			populateList(CellRangeAddress.valueOf("A4:A100"), logsNumber, sheet);
			populateList(CellRangeAddress.valueOf("B4:B100"), logsDate, sheet);
			populateList(CellRangeAddress.valueOf("C4:C100"), logsStart, sheet);
			populateList(CellRangeAddress.valueOf("D4:D100"), logsStop, sheet);
			populateList(CellRangeAddress.valueOf("E4:E100"), logsTime, sheet);
			populateList(CellRangeAddress.valueOf("F4:F100"), logsLifeCycle, sheet);
			populateList(CellRangeAddress.valueOf("G4:G100"), logsCategory, sheet);
			populateList(CellRangeAddress.valueOf("H4:H100"), logsDeliverable, sheet);
			workbook.close();

	}

	public static ObservableList<String> getLogsNumbers() {
			return logsNumber;
	}
	public static ObservableList<String> getLogsDate() {
			return logsDate;
	}
	public static ObservableList<String> getLogsStart() {
			return logsStart;
	}	
	public static ObservableList<String> getLogsStop() {
			return logsStop;
	}	
	public static ObservableList<String> getLogsTime() {
			return logsTime;
	}	
	public static ObservableList<String> getLogsLifeCycle() {
			return logsLifeCycle;
	}	
	public static ObservableList<String> getLogsCategory() {
			return logsCategory;
	}
	public static ObservableList<String> getLogsDeliverable() {
			return logsDeliverable;
	}				
	public static Duration calculateDeltaTime(LocalDateTime startTime, LocalDateTime stopTime) {
        return Duration.between(startTime, stopTime);
    }

	public void updateExcel() throws IOException {
			FileInputStream file = new FileInputStream(new File("effortlogger\\effortlogger\\src\\main\\resources\\EffortLoggerData.xlsm"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

            // Create a new row and add cells
			int nextRow = findNextRow(CellRangeAddress.valueOf("A4:A100"), sheet);
			System.out.println(nextRow);
            Row newRow = sheet.createRow(nextRow); // Add a row after the last row

            Cell numberCell = newRow.createCell(0);
            numberCell.setCellValue(nextRow-2);

            Cell dateCell = newRow.createCell(1);
			LocalDate currentDate = LocalDate.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	String formattedDate = currentDate.format(formatter);
            dateCell.setCellValue(formattedDate);

            Cell startCell = newRow.createCell(2);
			DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("HH:mm:ss");
			String formattedStart = startTime.format(formatterStart);
            startCell.setCellValue(formattedStart);

			Cell stopCell = newRow.createCell(3);
			DateTimeFormatter formatterStop = DateTimeFormatter.ofPattern("HH:mm:ss");
			String formattedStop = startTime.format(formatterStop);
            stopCell.setCellValue(formattedStop);

			Cell timeCell = newRow.createCell(4);
			Duration delta = calculateDeltaTime(startTime, endTime);
			long minutes = delta.toMinutes();
			timeCell.setCellValue(Long.toString(minutes));

			Cell lifeCycleCell = newRow.createCell(5);
			lifeCycleCell.setCellValue(lifeCycleStep.getValue());

			Cell categoryCell = newRow.createCell(6);
			categoryCell.setCellValue(effortCategory.getValue());

			Cell deliverableCell = newRow.createCell(7);
			deliverableCell.setCellValue(subordinates.getValue());

            file.close();

            // Save the updated workbook
            FileOutputStream outputStream = new FileOutputStream("effortlogger\\effortlogger\\src\\main\\resources\\EffortLoggerData.xlsm");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
	}

	public static int findNextRow(CellRangeAddress range, XSSFSheet sheet) {
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
				return rowNum;
			}
            if (cell.toString().equals("")) {
				return rowNum;
            }
        }		
		return 0;
	}
	
	public static void readDefinitions() throws IOException {
			FileInputStream file = new FileInputStream(new File("effortlogger\\effortlogger\\src\\main\\resources\\EffortLoggerData.xlsm"));
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
	
	public void enterLog() throws IOException {
		updateExcel();
	}
}
