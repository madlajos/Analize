package main.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.view.Beolvas;

public class Analize implements Runnable {
	private boolean online;
	private boolean tus;
	private String folderPath;
	private String output = "C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results";
	ImageView img;

	public Analize(String folderPath){
		this.folderPath = folderPath;
		this.online = true;
	}

	public Analize(String folderPath, String output, boolean tus){
		this.folderPath = folderPath;
		this.online = false;
		this.output = output;
		this.tus = tus;
	}

	public int analyse() { 
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		int i;
		for (i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String path = folderPath + File.separator + listOfFiles[i].getName();
				try {
					Image image = new Image("file:" + path);
					img.setImage(image);
					System.out.println(i);
					analyseImage(path, listOfFiles[i].getName().replaceFirst("[.][^.]+$", ""));
				} catch (Exception e) {
					System.out.print("Baj van :( ");
					System.out.println(e.getMessage());
				}
			}
		}
		return --i;
	}


	private void analyseImage(String path, String filename) throws Exception {
		//System.out.println(path);
		ImagePlus imp = IJ.openImage(path);
		IJ.run(imp, "8-bit", "");
		IJ.setAutoThreshold(imp, "Default dark");
		IJ.setAutoThreshold(imp, "Moments dark");
		Prefs.blackBackground = false;
		IJ.run(imp, "Convert to Mask", "");
		IJ.run(imp, "Set Measurements...", "area perimeter shape feret's limit redirect=None decimal=2");
		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
		IJ.run(imp, "Analyze Particles...", "size=100-Infinity show=Outlines display exclude");
		System.setOut(old);
		if(online) {
			Online(baos, filename);
		}
		else {
			Offline(baos);
		}
	}





	private void Offline(ByteArrayOutputStream baos) throws Exception {
		File file = new File(output + File.separator + "Osszes.arff");
		file.createNewFile();

		/* String line = "@RELATION crystalform\n\n"
				+ "@ATTRIBUTE line NUMERIC\n@ATTRIBUTE area NUMERIC\n@ATTRIBUTE perim NUMERIC "
				+ "@ATTRIBUTE circ NUMERIC\n@ATTRIBUTE fereT NUMERIC\n@ATTRIBUTE fereTx NUMERIC\n@ATTRIBUTE ferety NUMERIC\n"
				+ "@ATTRIBUTE fereTangle NUMERIC\n@ATTRIBUTE minfereT NUMERIC\n@ATTRIBUTE ar NUMERIC\n@ATTRIBUTE round NUMERIC\n"
				+ "@ATTRIBUTE solidity NUMERIC\n@ATTRIBUTE form {Tus, nem}\n\n@DATA\n";
		// Files.write(Paths.get("C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results\\Osszes.arff"), line.getBytes(), StandardOpenOption.APPEND);
		line = "";*/ 
		String form = tus ? "Tus" : "Nem";
		try {
			Files.write(Paths.get(output + File.separator + "Temp.arff"), baos.toString().getBytes());
		}catch (IOException e) {
			System.out.println(e.getMessage());
			//exception handling left as an exercise for the reader
		}




		try (BufferedReader br = new BufferedReader(new FileReader(output + File.separator + "Temp.arff"))) {
			String line;
			int c = 0;
			while ((line = br.readLine()) != null) {
				String temp = c == 0 ? "Form" : form;
				line = line.replace("\t" , ",");
				line = line + "," + temp + "\r\n";
				if (c > 0) {
					Files.write(Paths.get(output + File.separator + "Osszes.arff"), line.getBytes(), StandardOpenOption.APPEND);
				}
				c++;
			}
			Beolvas.adatbeolvasas(folderPath);
		}
	}

	private void Online(ByteArrayOutputStream baos, String filename) throws Exception {
		OutputStream outputStream = new FileOutputStream(output + File.separator + filename + ".txt");
		baos.writeTo(outputStream);	


	}

	public void setImageView(ImageView img){
		this.img = img;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		analyse();
	}
}
