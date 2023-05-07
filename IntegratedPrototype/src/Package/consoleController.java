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

public class consoleController {
	
	private String logName = "testCSV.csv";

		@FXML AnchorPane conScene;
		
		@FXML Label clock;
		
		@FXML TextField proj;
		@FXML TextField cycle;
		@FXML TextField cat;
		@FXML TextField del;
		
		@FXML Button startAc;
		@FXML Button stopAc;
		@FXML Button exit;
	
		long starttime;
		
		@FXML void startActivity(ActionEvent event) {
			clock.setText("Clock is Running");
			clock.setStyle("-fx-background-color: lime;");
			starttime = System.currentTimeMillis();
		}
		
		@FXML void stopActivity(ActionEvent event) {
			if(!clock.getText().equals("Clock is Stopped") && !cycle.getText().equals("") && !cat.getText().equals("") && !del.getText().equals("")) {
				clock.setText("Clock is Stopped");
				clock.setStyle("-fx-background-color: red;");
				
				long elapsedTime = System.currentTimeMillis() - starttime;
				int hours = (int) (elapsedTime / (60 * 60 * 1000));
	            int minutes = (int) ((elapsedTime / (60 * 1000)) % 60);
	            int seconds = (int) ((elapsedTime / 1000) % 60);
	            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	            
	            //code to add to csv file
	            String taskLine = ",,,"+timeString+","+cycle.getText()+","+cat.getText()+","+del.getText()+"\n";
	            try {
	            	CSVUtils.writeTask(taskLine, logName);
	            }
	            catch (Exception e) {
	            	System.out.println(e.getMessage());
	            	Alert alert = new Alert(AlertType.ERROR, "Error writing to CSV");
	            	alert.showAndWait();
	            }
	            
	            proj.setText("");
	            cycle.setText("");
	            cat.setText("");
	            del.setText("");
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
