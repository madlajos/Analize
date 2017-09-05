package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import javafx.scene.text.Text;
import main.util.IntervalLoop;


public class Beolvas {

	String folderPath;
	public static void adatbeolvasas (String folderPath, String filePath, Text txt1, Text txt2, IntervalLoop ip) throws Exception {
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

			d = (array[4] + array[8])/2 ;
			dsum += d;
			ARsum += array[10];
			count ++;
			
			double V = 4.0*Math.PI*Math.pow(d/2, 3)/3;
			Vsum += V;
			ip.addItem(d);
		}

		br.close();

		double davg = dsum/count;
		double ARavg = ARsum/count;
		
		txt1.setText(String.format("%.1f", davg) + " µm");
		txt2.setText(String.format("%.2f", ARavg));

		//Clear Osszes.arff
		PrintWriter pw = new PrintWriter(filePath);
		pw.close();
	}
}