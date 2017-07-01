package main.controller;

import java.awt.Label;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
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
	NumberAxis lineYAxis, barYAxis;
	@FXML
	CategoryAxis lineXAxis, barXAxis;
	@FXML
	LineChart<String, Number> lineChart;
	@FXML
	BarChart<String, Number> barChart;
	
	public void trigger() {
		
		AnalyzeHandler a = new AnalyzeHandler("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		//AnalyzeHandler a = new AnalyzeHandler("/Users/istvanhoffer/Desktop/images");
		a.setImageView(img);
		a.setTextArea(ta1, ta2);
		setupLinechart(a);
		setupBarchart(a);
		Thread t = new Thread(a);
		t.start();	
	}
	
	private void setupLinechart(AnalyzeHandler a){
		XYChart.Series<String, Number> series10 = new XYChart.Series<>();
		series10.setName("Dv10");
		XYChart.Series<String, Number> series50 = new XYChart.Series<>();
		series50.setName("Dv50");
		XYChart.Series<String, Number> series90 = new XYChart.Series<>();
		series90.setName("Dv90");
		
		lineChart.getData().add(series10);
		lineChart.getData().add(series50);
		lineChart.getData().add(series90);
		lineChart.setCreateSymbols(false);
		a.setLinechartSeries(series10, series50, series90);
	}
	
	private void setupBarchart(AnalyzeHandler a){
		XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
		barSeries.setName("Bar Chart");
		barChart.setAnimated(false);
		barChart.setLegendVisible(false);
		barChart.getData().add(barSeries);
		
		a.setBarchartSeries(barSeries);
	}
}