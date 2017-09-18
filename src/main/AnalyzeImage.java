package main;

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

public class AnalyzeImage {
	private AnalyzeMode mode;
	private String path, filename, output;
	private boolean tus;

	public AnalyzeImage(AnalyzeMode mode, String path, String filename, String output, boolean tus){
		this.path = path;
		this.filename = filename;
		this.mode = mode;
		this.output = output;
		this.tus = tus;
	}

	public void startAnalyze(){
		try {
			analyzeImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyzeImage() throws Exception {
		ImagePlus imp = IJ.openImage(path);
		IJ.run(imp, "8-bit", "");
		//IJ.run(imp, "Set Scale...", "distance=18 known=110 unit=um");
		//IJ.setAutoThreshold(imp, "Li dark");
		IJ.setRawThreshold(imp, 88, 255, null);
		Prefs.blackBackground = false;
		IJ.run(imp, "Convert to Mask", "");
		IJ.run(imp, "Set Measurements...", "area perimeter shape feret's limit redirect=None decimal=2");
		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
		IJ.run(imp, "Analyze Particles...", "size=80-Infinity display exclude");
		System.setOut(old);
		outputHandler(baos, filename);
	}

	private void outputHandler(ByteArrayOutputStream baos, String filename) throws Exception{
		switch(mode){
		case GRANULALAS:
			Granulalas(baos);
			break;
		case ONLINE:
			Online(baos, filename);
			break;
		case OFFLINE:
			Offline(baos);
			break;
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
			File Temp = new File(output + File.separator + "Temp.arff");
			Temp.createNewFile();
			Files.write(Paths.get(output + File.separator + "Temp.arff"), baos.toString().getBytes());
		}catch (IOException e) {
			System.out.println(e.getMessage());

		}

		try (BufferedReader br = new BufferedReader(new FileReader(output + File.separator + "Temp.arff"))) {
			String line;
			int c = 0;
			while ((line = br.readLine()) != null) {
				String temp = c == 0 ? "Form" : form;
				line = line.replace("\t" , ",");
				line = line + "," + temp + "\r\n";
				//if (c > 0) {	
					if(c >= 0) {
						Files.write(Paths.get(output + File.separator + "Osszes.arff"), line.getBytes(), StandardOpenOption.APPEND);
					}
					c++;
				}
			}
		}

		private void Granulalas(ByteArrayOutputStream baos) throws Exception{
			File file = new File(output + File.separator + "Osszes.arff");
			file.createNewFile();
			try {
				File Temp = new File(output + File.separator + "Temp.arff");
				Temp.createNewFile();
				Files.write(Paths.get(output + File.separator + "Temp.arff"), baos.toString().getBytes());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			writeFile(output + File.separator + "Temp.arff");
		}

		private void Online(ByteArrayOutputStream baos, String filename) throws Exception {
			OutputStream outputStream = new FileOutputStream(output + File.separator + filename + ".txt");
			baos.writeTo(outputStream);
			File file = new File(output + File.separator + "Osszes.arff");
			file.createNewFile();

			writeFile(output + File.separator + filename + ".txt");
		}

		private void writeFile(String filename) throws Exception{
			try(BufferedReader br = new BufferedReader(new FileReader(filename))){
				int c = 0;
				String line;
				while ((line = br.readLine()) != null) {
					line = line.replace("\t" , ",");
					line += "\r\n";
					//if (c > 0) {
						if (c >= 0) {
						Files.write(Paths.get(output + File.separator + "Osszes.arff"), line.getBytes(), StandardOpenOption.APPEND);
					}
					c++;
				}
			}
		}
	}
