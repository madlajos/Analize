package main.view;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.TextArea;


public class Beolvas {
	
	String folderPath;
	public static void adatbeolvasas (String folderPath, String filePath, TextArea ta1, TextArea ta2) throws Exception {
		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		double [] array = null;
		double dsum = 0;
		double ARsum = 0;
		int count = 0;
		ArrayList<Double> arrlist1 = new ArrayList<>(); //Area
		ArrayList<Double> arrlist2 = new ArrayList<>(); //Aspect Ratio
		
		while((line = br.readLine()) != null){
			String[] b = line.split(splitBy);
			array = Arrays.stream(b).mapToDouble(Double::parseDouble).toArray();
			
			//System.out.println(Arrays.toString(array));
			//System.out.println(array[1]);
			
			
			//a grafikonokhoz lehet arraylist kéne, azért hagytam itt
			//arrlist1.add((double) Math.round(Math.sqrt(4*array[1]/Math.PI)));
			//arrlist2.add(array[10]);
			
			
			dsum += Math.round(Math.sqrt(4*array[1]/Math.PI));
			ARsum += array[10];
			count ++;
			
		}
		br.close();
		/*
		System.out.println(dsum);
		System.out.println("count = " + count);
		System.out.println(ARsum);
		*/
		double davg = dsum/count;
		double ARavg = ARsum/count;
		
		ta1.appendText(String.format("%.1f \n", davg));
		ta2.appendText(String.format("%.2f \n", ARavg));
		
		PrintWriter pw = new PrintWriter("C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results\\Osszes.arff");
		pw.close();
		
		/*
		dsum = 0;
		ARsum = 0;
		count = 0;
		
		arrlist1.clear();
		arrlist2.clear();
		*/
		
	}
}