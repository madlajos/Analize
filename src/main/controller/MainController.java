package main.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private void onlineClicked(){
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak", true);
		a.analyse();
	}
	
	@FXML
	private void offlineClicked(){
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak", false);
		int i = a.analyse();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(i + " kép elemezve");

		alert.showAndWait();
	}
}