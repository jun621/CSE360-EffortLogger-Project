package Package;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// Author: Junichi Koizumi
// CSE360 M17
// 4/2/2023
public class LoginPrototype {
	//variables to track login attempts and manage lockout
    private int allowed_attempts = 3;
    private int attempts_used = 0;
    
    private Scene loginScene; //main login Scene
    
    //hard coded test credentials
    private String test_user = "a";
    private String test_pwd = "a";


    public Scene getLoginScene(Stage currentStage) {
        // Create the login scene
        GridPane loginRoot = new GridPane();
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.setHgap(10);
        loginRoot.setVgap(10);
        loginRoot.setPadding(new Insets(25, 25, 25, 25));

        Label userNameLabel = new Label("Username:");
        TextField userNameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Log in");
        loginButton.setOnAction(event -> {
            String username = userNameField.getText().trim();
            String password = passwordField.getText().trim();


            //validate password for the user to login
            if (validate(username, password)) {
				try {
	            	Parent root = FXMLLoader.load(getClass().getResource("Main Screen.fxml"));
	                currentStage.setScene(new Scene(root, 635, 425));
				} catch (IOException e) {
					e.printStackTrace();
				}
                // Open main window
            } else {
            	//increment attempts and check if lockout needed
                attempts_used++;
                if (allowed_attempts < attempts_used) {
                } else {
                	//display warning to user
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid username or password.");
                    alert.showAndWait();
                }
            }
        });
        
        //format pane
        HBox hbLoginButton = new HBox(10);
        hbLoginButton.setAlignment(Pos.BOTTOM_RIGHT);
        hbLoginButton.getChildren().add(loginButton);

        loginRoot.add(userNameLabel, 0, 0);
        loginRoot.add(userNameField, 1, 0);
        loginRoot.add(passwordLabel, 0, 1);
        loginRoot.add(passwordField, 1, 1);
        loginRoot.add(hbLoginButton, 1, 2);

        loginScene = new Scene(loginRoot, 400, 350);
        return loginScene;
        //primaryStage.setScene(loginScene);
        //primaryStage.show();
    }


    // If user enters invalid credentials function will guide them to update username/password
    public Scene ResetPasswordScene(Stage primaryStage) {
        // Create the reset password scene
        GridPane resetRoot = new GridPane();
        resetRoot.setAlignment(Pos.CENTER);
        resetRoot.setHgap(10);
        resetRoot.setVgap(10);
        resetRoot.setPadding(new Insets(30, 30, 30, 30));

        Label warningLabel = new Label("Your account has been locked after multiple login attempts.");
        Label resetPasswordLabel = new Label("To reset your password, please enter a new username and password below.");

        Label newUserNameLabel = new Label("New Username:");
        TextField newUserNameField = new TextField();
        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();

        Button resetButton = new Button("Reset Password");
        
        //reseting password logic
        resetButton.setOnAction(event -> {
            String newUsername = newUserNameField.getText().trim();
            String newPassword = newPasswordField.getText().trim();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a new username and password.");
                alert.showAndWait();
            } else {
            	test_user = newUsername;
            	test_pwd = newPassword;
                System.out.println("Password reset successful!");
                attempts_used = 0;
                primaryStage.setScene(loginScene);
            }
        });

        HBox hbResetButton = new HBox(10);
        hbResetButton.setAlignment(Pos.BOTTOM_RIGHT);
        hbResetButton.getChildren().add(resetButton);
        resetRoot.add(warningLabel, 0, 0, 2, 1);
        resetRoot.add(resetPasswordLabel, 0, 1, 2, 1);
        resetRoot.add(newUserNameLabel, 0, 2);
        resetRoot.add(newUserNameField, 1, 2);
        resetRoot.add(newPasswordLabel, 0, 3);
        resetRoot.add(newPasswordField, 1, 3);
        resetRoot.add(hbResetButton, 1, 4);

        return new Scene(resetRoot, 400, 300);
    }
    
    public boolean validate(String username, String password) {
    	if(username.equals(test_user) && password.equals(test_pwd))
    		return true;
    	return false;
    }
}



