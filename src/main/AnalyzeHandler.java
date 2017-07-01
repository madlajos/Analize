package main;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import main.util.IntervalLoop;

public class AnalyzeHandler implements Runnable {
	private final double DMIN = 0.01;
	private final double DMAX = 10000;
	private final double NKEP = 10;
	
	private boolean tus;
	private final AnalyzeMode mode;
	private String folderPath;
	private boolean deleteAnalized = false;
	private String output = "C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results";
	//private String output = "/Users/istvanhoffer/Desktop/Results";
	ImageView img;
	TextArea ta1, ta2;
	TextFlow tf1;
	XYChart.Series series10, series50, series90, barSeries;
	IntervalLoop ip = new IntervalLoop(DMIN, DMAX);;

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

	//granulálás
	public AnalyzeHandler(String folderPath, String output){
		this.folderPath = folderPath;
		this.output = output;
		mode = AnalyzeMode.GRANULALAS;
	}

	public void analyse() {
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
								Platform.runLater(() -> updateBarchart());
								ip = new IntervalLoop(DMIN, DMAX);
							}
							Beolvas.adatbeolvasas(folderPath, output + File.separator + "Osszes.arff", ta1, ta2, ip);
							String timestamp = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
							series10.getData().add(new XYChart.Data(timestamp, ip.getPercentile(10)));
							series50.getData().add(new XYChart.Data(timestamp, ip.getPercentile(50)));
							series90.getData().add(new XYChart.Data(timestamp, ip.getPercentile(90)));
							
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
	
	private void updateBarchart(){
		System.out.println("halo");
		for(int i = 0; i < 100; i++){
			System.out.print(ip.getIntervalVpercent(i) +" ");
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

	public void setTextArea(TextArea ta1, TextArea ta2){
		this.ta1 = ta1;
		this.ta2 = ta2;
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