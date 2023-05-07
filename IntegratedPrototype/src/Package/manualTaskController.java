package Package;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class manualTaskController {
	@FXML AnchorPane conScene;
	private String logName = "testCSV.csv";
	
	
    @FXML TextField lifeCycleField;
    @FXML TextField categoryField;
    @FXML TextField deliverableField;
    @FXML TextField timeTextField;
    @FXML TextField dateTextField;
	
	@FXML Label sucess;
	
	@FXML Button add;
	@FXML Button exit;
	
	@FXML void addTask(ActionEvent event) {
			// Get the text from the text fields
            //String task = taskTextField.getText();
		if(!(!dateTextField.getText().equals("") && !lifeCycleField.getText().equals("") && !categoryField.equals("") && !deliverableField.getText().equals("") && !timeTextField.getText().equals(""))) {
			sucess.setText("Error: One or more fields are empty");
            sucess.setStyle("-fx-text-fill: red;");
            return;
		}
        	String lifecycle = lifeCycleField.getText();
        	String category = categoryField.getText();
        	String deliverable = deliverableField.getText();
            String time = timeTextField.getText();
            String date = dateTextField.getText();
            
            // Check if the date format is valid
            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                sucess.setText("Error: Date is formatted incorrectly");
                sucess.setStyle("-fx-text-fill: red;");
                return;
            }
            
            //Convert time
            int hours = Integer.parseInt(time) / 60;
            int minutes = Integer.parseInt(time) % 60;
            String timeString = hours +":"+ minutes +":00";
            
            /*
            // Log the task details to the console
            System.out.println("Task: " + task);
            System.out.println("Time Spent (mins): " + time);
            System.out.println("Date (dd-mm-yyyy): " + date);
            */
            
            // Append line to log
            //Create a csv line
            String taskLine = date+",,"+timeString+","+lifecycle+","+category+","+deliverable+"\n";
            try {
            	CSVUtils.writeTask(taskLine, logName);
            	sucess.setText("Sucess! Entry added sucessfully");
            	sucess.setStyle("-fx-text-fill: green;");
            }
            catch (Exception err) {
            	System.out.println(err.getMessage());
            	Alert alert = new Alert(AlertType.ERROR, "Error writing to CSV");
            	alert.showAndWait();
            }
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
