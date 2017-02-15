package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class OfflineController {
	@FXML
	RadioButton rb1; // Tus
	@FXML
	RadioButton rb2; // Nem
	final ToggleGroup group = new ToggleGroup();
	@FXML
	private void initialize() {

		rb1.setToggleGroup(group);
		rb1.setSelected(true);

		rb2.setToggleGroup(group);

	}
	public void trigger() {
		int i = 0;
		if (rb1.isSelected()) {
			Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak", true);
			i = a.analyse();
		}
		else if (rb2.isSelected()) {
			Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak", false);
			i = a.analyse();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(i + " kép elemezve");
		alert.showAndWait();
	}
}