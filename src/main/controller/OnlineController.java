package main.controller;

import java.awt.Label;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class OnlineController {

@FXML
Label labeld;
@FXML
ImageView img;
@FXML
CheckBox cb1;
@FXML
TextArea ta1;
	public void trigger() {
		int i = 0;
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		a.setImageView(img);
		a.setTextArea(ta1);
		Thread t = new Thread(a);
		t.start();
		//a.analyse();
	}
}
