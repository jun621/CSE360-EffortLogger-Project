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

public class editorController {

	@FXML AnchorPane conScene;
	
	@FXML Button exit;
	
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
