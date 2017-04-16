package main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

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
	RadioButton rb3; // Granulálás
	@FXML
	Label imageLoc;
	@FXML
	Label outputLoc;
	Properties prop = new Properties();
	File properties = new File("src/config/defaultpaths.properties");


	final ToggleGroup group = new ToggleGroup();
	@FXML
	private void initialize() {
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		rb2.setToggleGroup(group);
		rb3.setToggleGroup(group);
		try {
			prop.load(new FileInputStream(properties));
			imageLoc.setText(prop.getProperty("offline_input"));
			outputLoc.setText(prop.getProperty("offline_output"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trigger() {
		int i = 0;
		if (rb1.isSelected()) {
			Analize a = new Analize(imagel.getAbsolutePath(), outputl.getAbsolutePath(), true);
			//i = a.analyse();
			Thread t = new Thread(a);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
		}
		else if (rb2.isSelected()) {
			Analize a = new Analize(imagel.getAbsolutePath(), outputl.getAbsolutePath(), false);
			//i = a.analyse();
			Thread t = new Thread(a);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
		}
		else if (rb3.isSelected()) {
			Analize a = new Analize(imagel.getAbsolutePath(), outputl.getAbsolutePath());
			//i = a.analyse();
			Thread t = new Thread(a);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
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
		
		OutputStream prop_output;
		try {
			prop_output = new FileOutputStream(properties);
			prop.setProperty("offline_input", imagel.getAbsolutePath());
			prop.store(prop_output, null);
			prop_output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void setOutputLoc(){
		DirectoryChooser dc = new DirectoryChooser();
		outputl = dc.showDialog(stage);
		outputLoc.setText(outputl.getAbsolutePath());
		prop.setProperty("offline_output", outputl.getAbsolutePath());
		//prop.store(prop_input, null);
	}
}