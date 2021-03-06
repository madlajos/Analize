package main.controller;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.view.MainApp;
import main.view.Offline;

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
		stage.setX((1920 - 1325) / 2);
        stage.setY((1080 - 745) / 2);
		// these two of them return the same stage
		scene.getStylesheets().add("main/view/Online.css");
		// Swap screen

		stage.setScene(scene);
		stage.setTitle("Online Image Analysis");
		//stage.getIcons().add(new Image("C:\\Users\\madla\\OneDrive\\Documents\\GitHub\\Analize\\src\\main\\view\\icon.bmp"));
	    
	}
	
	@FXML
	private void offlineClicked() throws IOException{
		Offline o = new Offline();
		o.start((Stage) offline.getScene().getWindow());
	}
	
}