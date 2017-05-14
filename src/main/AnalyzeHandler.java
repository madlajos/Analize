package main;

import java.io.File;
import java.nio.file.Files;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import main.view.Beolvas;

public class AnalyzeHandler implements Runnable {
	private boolean tus;
	private final AnalyzeMode mode;
	private String folderPath;
	private boolean deleteAnalized = false;
	//private String output = "C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results";
	private String output = "/Users/istvanhoffer/Desktop/Results";
	ImageView img;
	TextArea ta1, ta2;
	TextFlow tf1;

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
							Beolvas.adatbeolvasas(folderPath, output + File.separator + "Osszes.arff", ta1, ta2);
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
}