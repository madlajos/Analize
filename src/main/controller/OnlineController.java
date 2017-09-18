package main.controller;
import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.AnalyzeHandler;
import main.util.Arduino;


public class OnlineController extends Parent{
	@FXML
	ImageView img;
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
	Text txt1, txt2, txt3, txt4, txt5, rpmTxt, rpmTxt1, Dv50text, autotext1, autotext2, RPMtext;
	static SerialPort chosenPort;
	String setDv50 = "600 - 800 µm";
	String scannedFolder = "D:\\TDK Képek\\Mérés\\Javanak";
	
	
	//Sliderclicknél értéket váltson
	public void setSliderVal(){
		int a = (int) (slider.getValue() + 0.2);
		if (a == 0) {
			//automatic mode
			slider.setValue(1);
			rpmSlider.setDisable(true);
			rpmTxt.setFill(Color.DARKGRAY);
			rpmTxt1.setFill(Color.DARKGRAY);
			Dv50text.setVisible(true);
			autotext1.setFill(Color.BLACK);
			Dv50text.setText(setDv50);
			autotext2.setFill(Color.BLACK);
			RPMtext.setVisible(true);
		}
		else {
			//manual mode
			slider.setValue(0);
			rpmSlider.setDisable(false);
			rpmTxt.setFill(Color.BLACK);
			rpmTxt1.setFill(Color.BLACK);
			Dv50text.setVisible(false);;
			autotext1.setFill(Color.DARKGRAY);
			autotext2.setFill(Color.DARKGRAY);
			RPMtext.setVisible(false);
		}
		AnalyzeHandler b = new AnalyzeHandler(scannedFolder);
		b.getMode(slider);
	}

	public void updateRPM(){
		AnalyzeHandler c = new AnalyzeHandler(scannedFolder);
		c.getRPM(rpmSlider);
		rpmTxt.setText(String.format("%.2f", c.getRPM(rpmSlider)));
		Arduino.sendData(c.getRPM(rpmSlider) * 25);
	}


	public static SerialPort getChosenPort() {
		return chosenPort;
	}

	public void trigger() {
		AnalyzeHandler a = new AnalyzeHandler(scannedFolder);
		//AnalyzeHandler a = new AnalyzeHandler("/Users/istvanhoffer/Desktop/images");
		a.setImageView(img);
		a.setTextArea(txt1, txt2, txt3, txt4, txt5, RPMtext);
		a.getMode(slider);
		a.getRPM(rpmSlider);
		setupLinechart(a);
		setupBarchart(a);
		Thread t = new Thread(a);	
		startButton.setDisable(true);
		t.start();
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

	public void searchPorts(){
		comboBox.setItems(Arduino.getPortList());
	}

	private void setupLinechart(AnalyzeHandler a){
		XYChart.Series<String, Number> series10 = new XYChart.Series<>();
		series10.setName("Dv10");
		XYChart.Series<String, Number> series50 = new XYChart.Series<>();
		series50.setName("Dv50");
		XYChart.Series<String, Number> series90 = new XYChart.Series<>();
		series90.setName("Dv90");
		lineYAxis.setForceZeroInRange(false);
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
		barXAxis.setLabel("Particle Size (μm)");
		barYAxis.setLabel("Volume (%)");
		barChart.getData().add(barSeries);
		a.setBarchartSeries(barSeries);
	}
}