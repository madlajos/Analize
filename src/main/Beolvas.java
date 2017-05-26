package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Beolvas {

	String folderPath;
	public static void adatbeolvasas (String folderPath, String filePath, TextArea ta1, TextArea ta2) throws Exception {
		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		double [] array = null;
		double d = 0;
		double dsum = 0;
		double ARsum = 0;
		int count = 0;
		double V = 0;
		double Vsum = 0;
		//ArrayList<Double> arrlist1 = new ArrayList<>(); //Area
		//ArrayList<Double> arrlist2 = new ArrayList<>(); //Aspect Ratio

		while((line = br.readLine()) != null){
			String[] b = line.split(splitBy);
			array = Arrays.stream(b).mapToDouble(Double::parseDouble).toArray();

			//System.out.println(Arrays.toString(array));
			//System.out.println(array[1]);


			//a grafikonokhoz lehet arraylist k√©ne, az√©rt hagytam itt
			//arrlist1.add((double) Math.round(Math.sqrt(4*array[1]/Math.PI)));
			//arrlist2.add(array[10]);

			d = Math.round(Math.sqrt(4*array[1]/Math.PI));
			dsum += d;
			ARsum += array[10];
			count ++;
			// V-t m·shogy kell sz·molni hallÛ
			V = 4.0*Math.PI*Math.pow(Math.round(Math.sqrt(4*array[1]/Math.PI))/2, 3)/3;
			Vsum += V;
		}


		br.close();

		/*
		System.out.println(dsum);
		System.out.println("count = " + count);
		System.out.println(ARsum);
		 */
		double davg = dsum/count;
		double ARavg = ARsum/count;

		ta1.appendText(String.format("%.1f\n", davg));
		ta2.appendText(String.format("%.2f\n", ARavg));


		//Osszes.arff-et √ºress√© teszi
		PrintWriter pw = new PrintWriter("C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results\\Osszes.arff");
		pw.close();

	}
}
