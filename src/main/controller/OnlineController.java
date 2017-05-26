package main.controller;

import java.awt.Label;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
	TextArea ta1, ta2;
	@FXML
	NumberAxis xAxis, yAxis;
	@FXML
	LineChart<Number, Number> lineChart;
	XYChart.Series series = new XYChart.Series();
	
	public void trigger() {
		lineChart.getData().add(series);
		AnalyzeHandler a = new AnalyzeHandler("/Users/istvanhoffer/Develop/Analize/kepek");
		a.setImageView(img);
		a.setTextArea(ta1, ta2);
		Thread t = new Thread(a);
		//t.start();
		
	}
	public void test() throws InterruptedException {
		series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
	}
	public void test2() {
		series.getData().add(new XYChart.Data(5, 10));
	}
	
}
