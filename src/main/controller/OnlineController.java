package main.controller;

import java.awt.Label;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import main.AnalyzeHandler;

public class OnlineController {
	@FXML
	Label labeld;
	@FXML
	ImageView img;
	@FXML
	CheckBox cb1;
	@FXML
	TextArea ta1;
	@FXML
	TextArea ta2;
	@FXML
	NumberAxis xAxis;
	@FXML
	NumberAxis yAxis;
	@FXML
	LineChart<String, Number> lineChart;

	public void trigger() {
		AnalyzeHandler a = new AnalyzeHandler("/Users/istvanhoffer/Develop/Analize/kepek");
		a.setImageView(img);
		a.setTextArea(ta1, ta2);
		Thread t = new Thread(a);
		t.start();
	}
}
