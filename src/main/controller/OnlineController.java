package main.controller;

import java.awt.Label;
import java.io.IOException;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.AnalyzeHandler;
import main.util.Arduino;


public class OnlineController extends Parent{
	@FXML
	Label labeld;
	@FXML
	ImageView img;
	@FXML
	CheckBox cb1;
	@FXML
	TextArea ta1, ta2, ta3;
	@FXML
	NumberAxis lineYAxis, barYAxis;
	@FXML
	CategoryAxis lineXAxis, barXAxis;
	@FXML
	LineChart<String, Number> lineChart;
	@FXML
	BarChart<String, Number> barChart;
	@FXML
	Slider slider, rpmSlider;
	@FXML
	ComboBox<String> comboBox;
	@FXML
	Button connectButton, startButton;
	@FXML
	Text txt1;
	static SerialPort chosenPort;

	
	//Sliderclickn�l �rt�ket v�ltson
	public void setSliderVal(){
		int a = (int) (slider.getValue() + 0.2);
		if (a == 0) {
			slider.setValue(1);
		}
		else {
			slider.setValue(0);
		}
		AnalyzeHandler b = new AnalyzeHandler("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		b.getMode(slider);
	}
	
	public void updateRPM(){
		
		//System.out.println(rpmSlider.getValue());
		AnalyzeHandler c = new AnalyzeHandler("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		c.getRPM(rpmSlider);
		System.out.println("rpm updatelve");
	}


	public static SerialPort getChosenPort() {
		return chosenPort;
	}

	public void trigger() {
		startButton.setDisable(true);
		AnalyzeHandler a = new AnalyzeHandler("C:\\Users\\madla\\Google Drive\\TDK\\Java\\kepek hofinak");
		//AnalyzeHandler a = new AnalyzeHandler("/Users/istvanhoffer/Desktop/images");
		a.setImageView(img);
		a.setTextArea(ta1, ta2);
		a.getMode(slider);
		a.getRPM(rpmSlider);
		setupLinechart(a);
		setupBarchart(a);
		comboBox.setItems(Arduino.getPortList());
		Thread t = new Thread(a);
		t.start();
		txt1.setText("asd");
	}

	public void connect() {
		chosenPort = SerialPort.getCommPort(comboBox.getValue());
		chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		if(chosenPort.openPort()) {
			connectButton.setText("Disconnect");
			comboBox.setDisable(true);
		}
		else {
			chosenPort.closePort();
			comboBox.setDisable(false);
			connectButton.setText("Connect");
		}
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