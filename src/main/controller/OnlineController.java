package main.controller;

import java.awt.Label;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
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
	NumberAxis yAxis;
	@FXML
	CategoryAxis xAxis;
	@FXML
	LineChart<String, Number> lineChart;
	
	public void trigger() {
		XYChart.Series series10 = new XYChart.Series();
		series10.setName("Dv10");
		XYChart.Series series50 = new XYChart.Series();
		series50.setName("Dv50");
		XYChart.Series series90 = new XYChart.Series();
		series90.setName("Dv90");
		
		lineChart.getData().add(series10);
		lineChart.getData().add(series50);
		lineChart.getData().add(series90);
		lineChart.setCreateSymbols(false);
		/*
		xAxis.setAutoRanging(false);
	    xAxis.setLowerBound(0);
	    xAxis.setUpperBound(120);
	    */
		AnalyzeHandler a = new AnalyzeHandler("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		a.setImageView(img);
		a.setTextArea(ta1, ta2);
		a.setSeries(series10, series50, series90);
		Thread t = new Thread(a);
		t.start();
		
	}
}
