package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

import javafx.scene.control.TextArea;
import main.util.IntervalLoop;


public class Beolvas {

	String folderPath;
	public static void adatbeolvasas (String folderPath, String filePath, TextArea ta1, TextArea ta2, IntervalLoop ip) throws Exception {
		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		double [] array = null;
		double d = 0;
		double dsum = 0;
		double ARsum = 0;
		int count = 0;
		double Vsum = 0;
		

		while((line = br.readLine()) != null){
			String[] b = line.split(splitBy);
			array = Arrays.stream(b).mapToDouble(Double::parseDouble).toArray();

			//System.out.println(Arrays.toString(array));
			//System.out.println(array[1]);



			d = Math.sqrt(4*array[1]/Math.PI);
			dsum += d;
			ARsum += array[10];
			count ++;
			// V-t m�shogy kell sz�molni hall�
			double V = 4.0*Math.PI*Math.pow(d/2, 3)/3;
			Vsum += V;
			ip.addItem(d);
			
		}


		br.close();

		double davg = dsum/count;
		double ARavg = ARsum/count;
		
		ta1.appendText(String.format("\n%.1f", davg));
		ta2.appendText(String.format("\n%.2f", ARavg));


		//Osszes.arff-et üressé teszi
		PrintWriter pw = new PrintWriter(filePath);
		pw.close();
	}
}