package main.controller;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.view.MainApp;
import javafx.scene.control.Button;

public class MainController {
	@FXML
	private Button online;
	@FXML
	private Button offline;

	private MainApp mainApp;

	@FXML
	private void initialize() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}
	
	@FXML
	private void onlineClicked() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("Online.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        
		Stage stage = (Stage) online.getScene().getWindow();
	    // these two of them return the same stage
	    // Swap screen
	    stage.setScene(scene);
		/* Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak");
		a.analyse();
		ez a régi volt, amivel egybõl txtket gyárt */
	}
	
	@FXML
	private void offlineClicked() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("Offline.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        
		Stage stage = (Stage) offline.getScene().getWindow();
	    // these two of them return the same stage
	    // Swap screen
	    stage.setScene(scene);
	}
	
}