package main.view;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import javafx.scene.control.TextArea;


public class Beolvas {
	
	String folderPath;
	public static void adatbeolvasas (String folderPath, String filePath, TextArea ta) throws Exception {
		String splitBy = ",";
		File folder = new File(folderPath);
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		double [] array = null;
		double sum = 0;
		int  count = 0;
		ArrayList<Double> arrlist = new ArrayList<>();
		while((line = br.readLine()) != null){
			System.out.println(line);
			String[] b = line.split(splitBy);
			array = Arrays.stream(b).mapToDouble(Double::parseDouble).toArray();
			arrlist.add(array[1]);
			
			sum += array[1];
			System.out.println(array[1]);
			count ++;
			
			//Integer sumofNumbers = sumNumbers(array);
		
		}
		br.close();
		
		double avg = sum/count;
		ta.appendText(String.format("Value: %.2f\n", avg));
		System.out.println(arrlist);
		
	}
}