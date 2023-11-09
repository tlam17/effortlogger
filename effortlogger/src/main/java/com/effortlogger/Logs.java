package com.effortlogger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class Logs implements Initializable{

    ObservableList<Entry> initialData = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Entry, String> category;

    @FXML
    private TableColumn<Entry, String> date;

    @FXML
    private TableColumn<Entry, String> deliverable;

    @FXML
    private TableColumn<Entry, String> lifeCycle;

    @FXML
    private TableColumn<Entry, String> number;

    @FXML
    private TableColumn<Entry, String> start;

    @FXML
    private TableColumn<Entry, String> stop;

    @FXML
    private TableView<Entry> table;

    @FXML
    private TableColumn<Entry, String> time;


    public ObservableList<Entry> initializeData() {
        Console main = new Console();
        ObservableList<String> numberList = main.getLogsNumbers();
        ObservableList<String> dateList = main.getLogsDate();
        ObservableList<String> startList = main.getLogsStart();
        ObservableList<String> stopList = main.getLogsStop();
        ObservableList<String> timeList = main.getLogsTime();
        ObservableList<String> categoryList = main.getLogsCategory();
        ObservableList<String> deliverableList = main.getLogsDeliverable();
        ObservableList<String> lifeCycleList = main.getLogsLifeCycle();
        System.out.println(numberList);
        for (int i = 0; i<numberList.size(); i++) {
            initialData.add(new Entry(numberList.get(i), dateList.get(i), startList.get(i), stopList.get(i), timeList.get(i), lifeCycleList.get(i), categoryList.get(i), deliverableList.get(i)));
        }
        return initialData;
    }

    public void switchToConsole(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/console.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("EffortLogger");
        stage.setScene(scene);
        stage.show();
	}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("lol");
        number.setCellValueFactory(new PropertyValueFactory<Entry, String>("number"));
        date.setCellValueFactory(new PropertyValueFactory<Entry, String>("date"));
        start.setCellValueFactory(new PropertyValueFactory<Entry, String>("start"));
        stop.setCellValueFactory(new PropertyValueFactory<Entry, String>("stop"));
        time.setCellValueFactory(new PropertyValueFactory<Entry, String>("time"));
        lifeCycle.setCellValueFactory(new PropertyValueFactory<Entry, String>("lifeCycle"));
        category.setCellValueFactory(new PropertyValueFactory<Entry, String>("category"));
        deliverable.setCellValueFactory(new PropertyValueFactory<Entry, String>("deliverable"));
        table.setItems(initializeData());
    }

}
