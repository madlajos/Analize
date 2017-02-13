package main.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;

public class Analize {
	private boolean online;
	
	private String folderPath;

	public static void main(String[] Args) throws IOException {
		Analize a = new Analize("C:\\Users\\madla\\Google Drive\\TDK\\Java\\képek hofinak", true);
		a.analyse();
	}
	
	public Analize(String folderPath, boolean online){
		this.folderPath = folderPath;
		this.online = online;
	}

	public int analyse() {
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		int i;
		for (i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String path = folderPath + "\\" + listOfFiles[i].getName();
				try {
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
		ImagePlus imp = IJ.openImage(path);
		IJ.run(imp, "8-bit", "");
		IJ.setAutoThreshold(imp, "Default dark");
		IJ.setThreshold(imp, 187, 255);
		Prefs.blackBackground = false;
		IJ.run(imp, "Convert to Mask", "");
		IJ.run(imp, "Set Measurements...",
				"area centroid center perimeter bounding fit shape feret's integrated stack limit redirect=None decimal=1");

		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
		IJ.run(imp, "Analyze Particles...", "  show=Outlines display");
		System.setOut(old);
		if(online) {
			Online(baos, filename);
		}
		else {
			Offline(baos);
		}
	}
		
		
		


	private void Offline(ByteArrayOutputStream baos) {
		try {
		    Files.write(Paths.get("C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results\\Osszes.txt"), baos.toString().getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    System.out.println(e.getMessage());
			//exception handling left as an exercise for the reader
		}
	}
	
	private void Online(ByteArrayOutputStream baos, String filename) throws Exception {
		OutputStream outputStream = new FileOutputStream("C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results\\" + filename + ".txt");
		baos.writeTo(outputStream);
	}
}
