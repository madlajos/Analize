package main.controller;

import java.awt.Label;

import javafx.fxml.FXML;

public class OnlineController {

@FXML
Label labeld;

	public void trigger() {
		int i = 0;
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak");
		a.analyse();
	}
}