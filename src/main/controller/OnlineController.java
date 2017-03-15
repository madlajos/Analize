package main.controller;

import java.awt.Label;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class OnlineController {

@FXML
Label labeld;
@FXML
ImageView img;
	public void trigger() {
		int i = 0;
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak");
		a.analyse();
	}

}
