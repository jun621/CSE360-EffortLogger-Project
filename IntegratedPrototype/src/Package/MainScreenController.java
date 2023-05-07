package Package;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenController {
	@FXML AnchorPane mainScene;
	
	@FXML Button exit;
	@FXML Button console;
	@FXML Button scrub;
	@FXML Button mod;
	@FXML Button list;
	@FXML Button logout;
	@FXML Button man;
	
	
	@FXML void navConsole(ActionEvent event) {
		Stage stage = (Stage) mainScene.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("test.fxml"));
			stage.setScene(new Scene(root, 635, 425));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML void navScrub(ActionEvent event) {
		
		//TO DO Scrubbing prototype implementation
		
	}
	
	@FXML void navMod(ActionEvent event) {
		Stage stage = (Stage) mainScene.getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LogEditor.fxml"));
			stage.setScene(new Scene(root, 635, 425));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML void navList(ActionEvent event) {
		Stage stage = (Stage) mainScene.getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("logList.fxml"));
			stage.setScene(new Scene(root, 600, 400));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML void navManualTask(ActionEvent event) {
		Stage stage = (Stage) mainScene.getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("manualTask.fxml"));
			stage.setScene(new Scene(root, 635, 425));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML void logOut(ActionEvent event) {
		Stage stage = (Stage) mainScene.getScene().getWindow();
		LoginPrototype login = new LoginPrototype();
		stage.setScene(login.getLoginScene(stage));
	}
	
	@FXML void close(ActionEvent event) {
		Stage stage = (Stage) mainScene.getScene().getWindow();
		stage.close();
	}
}
