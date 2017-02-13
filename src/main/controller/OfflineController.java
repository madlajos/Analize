package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class OfflineController {
	@FXML
	RadioButton rb1;
	@FXML
	RadioButton rb2;
	final ToggleGroup group = new ToggleGroup();
	@FXML
	private void initialize() {

		rb1.setToggleGroup(group);
		rb1.setSelected(true);

		rb2.setToggleGroup(group);

	}
}