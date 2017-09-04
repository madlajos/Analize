package main;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.controller.OnlineController;
import main.util.Arduino;
import main.util.IntervalLoop;

public class AnalyzeHandler implements Runnable {
	private final double DMIN = 0.01;
	private final double DMAX = 10000;
	private final double NKEP = 1;

	private boolean tus;
	private final AnalyzeMode mode;
	private String folderPath;
	private boolean deleteAnalized = false;
	private String output = "C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results";
	//private String output = "/Users/istvanhoffer/Desktop/Results";
	ImageView img;
	Text txt1, txt2, txt3, txt4, txt5;
	TextFlow tf1;
	Slider slider, rpmSlider;
	XYChart.Series series10, series50, series90, barSeries;
	IntervalLoop ip = new IntervalLoop(DMIN, DMAX);
	double dv10, dv50, dv90;

	//online
	public AnalyzeHandler(String folderPath){
		this.folderPath = folderPath;
		mode = AnalyzeMode.ONLINE;
	}

	//offline
	public AnalyzeHandler(String folderPath, String output, boolean tus){
		this.folderPath = folderPath;
		this.output = output;
		this.tus = tus;
		mode = AnalyzeMode.OFFLINE;
	}

	//granul√°l√°s
	public AnalyzeHandler(String folderPath, String output){
		this.folderPath = folderPath;
		this.output = output;
		mode = AnalyzeMode.GRANULALAS;
	}

	public double analyse() {
		File folder = new File(folderPath);
		int c = 0;
		while (true){
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String path = folderPath + File.separator + listOfFiles[i].getName();
					if(!path.matches("(.*)\\.jpg")){
						continue;
					}
					try {
						Image image = new Image("file:" + path);
						if(mode == AnalyzeMode.ONLINE){
							img.setImage(image);
						}
						System.out.println("Current image: " + path);
						sendToAnalize(path, listOfFiles[i].getName().replaceFirst("[.][^.]+$", ""));
						if(mode == AnalyzeMode.ONLINE){
							if(c % NKEP == 0){
								IntervalLoop ipClone = ip;
								Platform.runLater(() -> updateBarchart(ipClone));
								ip = new IntervalLoop(DMIN, DMAX);
								Beolvas.adatbeolvasas(folderPath, output + File.separator + "Osszes.arff", txt1, txt2, ip);
								String timestamp = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());

								dv10 = ip.getPercentile(10);
								dv50 = ip.getPercentile(50);
								dv90 = ip.getPercentile(90);

								series10.getData().add(new XYChart.Data(timestamp, dv10));
								series50.getData().add(new XYChart.Data(timestamp, dv50));
								series90.getData().add(new XYChart.Data(timestamp, dv90));

								txt3.setText(String.format("%.1f", dv10) + " µm");
								txt4.setText(String.format("%.1f", dv50) + " µm");
								txt5.setText(String.format("%.1f", dv90) + " µm");

								if (getMode(slider) == 0){
									System.out.println(getRPM(rpmSlider));
									Arduino.sendData(getRPM(rpmSlider) * 25);
								}
								else{
									Random rand = new Random();
									double  n = 255 * (rand.nextDouble());
									//System.out.println(n * 5 / 255);
									System.out.printf("A voltmÈrın kb %s, voltot kell l·tni" , n * 5 / 255 );
									Arduino.sendData(n);
									//System.out.println(ip.getPercentile(90));
								}

								//Arduino.sendData(ip.getPercentile(90));
							}
							c++;
						}
						if(deleteAnalized){
							Files.delete(listOfFiles[i].toPath());
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}


	public double getRPM(Slider rpmSlider) {
		this.rpmSlider = rpmSlider;

		return rpmSlider.getValue();
	}

	public double getMode(Slider slider) {
		this.slider = slider;

		return slider.getValue();
	}

	private void updateBarchart(IntervalLoop ip){
		for(int i = 0; i < 100; i++){
			barSeries.getData().add(new XYChart.Data(new Integer(i).toString(), ip.getIntervalVpercent(i)));
		}
	}

	private void sendToAnalize(String path, String filename){
		AnalyzeImage ai = new AnalyzeImage(mode, path, filename, output, tus);
		ai.startAnalyze();
	}

	public void setImageView(ImageView img){
		this.img = img;
	}

	public void setTextArea(Text txt1, Text txt2, Text txt3, Text txt4, Text txt5){
		this.txt1 = txt1;
		this.txt2 = txt2;
		this.txt3 = txt3;
		this.txt4 = txt4;
		this.txt5 = txt5;
	}

	@Override
	public void run() {
		analyse();
	}

	public void setLinechartSeries(XYChart.Series<String, Number> s10, XYChart.Series<String, Number> s50, XYChart.Series<String, Number> s90){
		this.series10 = s10;
		this.series50 = s50;
		this.series90 = s90;
	}

	public void setBarchartSeries(XYChart.Series<String, Number> barSeries){
		this.barSeries = barSeries;
		for(int i = 0; i < 100; i++){
			this.barSeries.getData().add(new XYChart.Data(new Integer(i).toString(), 0));
		}
	}
}