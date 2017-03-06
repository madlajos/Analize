package main.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OfflineController {
	Stage stage;
	File imagel;
	File outputl;
	@FXML
	RadioButton rb1; // Tus
	@FXML
	RadioButton rb2; // Nem
	@FXML
	Label imageLoc;
	@FXML
	Label outputLoc;



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
			Analize a = new Analize(imagel.getAbsolutePath(), outputl.getAbsolutePath(), true);
			i = a.analyse();
		}
		else if (rb2.isSelected()) {
			Analize a = new Analize(imagel.getAbsolutePath(), outputl.getAbsolutePath(), false);
			i = a.analyse();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(i + " k�p elemezve");
		alert.showAndWait();
	}

	public void setStage(Stage s){
		stage = s;
	}

	@FXML
	private void setImageLoc(){
		DirectoryChooser dc = new DirectoryChooser();
		imagel = dc.showDialog(stage);
		imageLoc.setText(imagel.getAbsolutePath());
	}
	
	@FXML
	private void setOutputLoc(){
		DirectoryChooser dc = new DirectoryChooser();
		outputl = dc.showDialog(stage);
		outputLoc.setText(outputl.getAbsolutePath());
	}
}