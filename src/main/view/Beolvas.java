package main.view;
import java.io.BufferedReader;
import java.io.FileReader;
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
		double areasum = 0;
		double ARsum = 0;
		ArrayList<Double> arrlist1 = new ArrayList<>();
		ArrayList<Double> arrlist2 = new ArrayList<>();
		
		while((line = br.readLine()) != null){
			String[] b = line.split(splitBy);
			array = Arrays.stream(b).mapToDouble(Double::parseDouble).toArray();
			//System.out.println(Arrays.toString(array));
			
			arrlist1.add((double) Math.round(Math.sqrt(4*array[1]/Math.PI)));
			
			if (array.length == 12) { //az utolsó sor nincs 10 hosszú és errort dob rá
				arrlist2.add(array[10]);
			}
		
		}
		br.close();
		
		for (int i = 1; i < arrlist1.size()-1; i++) { 
			areasum += arrlist1.get(i);
			ARsum += arrlist2.get(i);
		}
		
		double areaavg = areasum/arrlist1.size();
		double ARavg = ARsum/arrlist2.size();
		ta1.appendText(String.format("%.1f \n", areaavg));
		ta2.appendText(String.format("%.1f \n", ARavg));
		//System.out.println(arrlist1);
		//System.out.println(arrlist2);
		
	}
}