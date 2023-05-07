package Package;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class logListController implements Initializable{
	@FXML AnchorPane conScene;
	
	@FXML TableView<log> table;
	
	@FXML TableColumn<log, String> number;
	@FXML TableColumn<log, String> date;
	@FXML TableColumn<log, String> dur;
	@FXML TableColumn<log, String> cycle;
	@FXML TableColumn<log, String> cat;
	@FXML TableColumn<log, String> del;
	
	public void initialize(URL url, ResourceBundle rb) {
		number.setCellValueFactory(new PropertyValueFactory<log,String>("number"));
		date.setCellValueFactory(new PropertyValueFactory<log, String>("date"));
		dur.setCellValueFactory(new PropertyValueFactory<log, String>("duration"));
		cycle.setCellValueFactory(new PropertyValueFactory<log, String>("cycle"));
		cat.setCellValueFactory(new PropertyValueFactory<log, String>("category"));
		del.setCellValueFactory(new PropertyValueFactory<log, String>("deliverable"));
		
		table.setItems(getList(3));
	}
	
	public ObservableList<log> getList(int start){
		ObservableList<log> items = FXCollections.observableArrayList();
		
		String[][] read = CSVUtils.readCSV(logName, start);
		for(int i=0; i<read.length; i++) {
			if(read[i].length <= 0) {
				break;
			} else {
				items.add(new log(read[i][0], read[i][1], read[i][4], read[i][5], read[i][6], read[i][7]));	
			}		
		}
		return items;
	}
	
	@FXML Button exit;
	private String logName = "testCSV.csv";
	int position = 13;
	
	@FXML void nextTen(ActionEvent event) {
		table.setItems(getList(position));
		position += 10;
	}
	
	@FXML void prevTen(ActionEvent event) {
		position -= 10;
		if(position < 3) {
			position = 3; //don't go past the start of the list
		}
		table.setItems(getList(position));
	}
	
	@FXML void navMain(ActionEvent event) {
		Stage stage = (Stage) conScene.getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main Screen.fxml"));
			stage.setScene(new Scene(root, 635, 425));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
