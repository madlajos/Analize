package main.controller;

import java.awt.Label;

import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
//import main.view.Linechart;

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
		int i = 0;
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		a.setImageView(img);
		a.setTextArea(ta1, ta2);
		//a.setTextArea(ta2);
		Thread t = new Thread(a);
		t.start();
		//a.analyse();
		
	
	    
	}
}
