package main.view;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Beolvas {

	String folderPath;
	@SuppressWarnings("rawtypes")
	public static void adatbeolvasas (String folderPath) throws Exception {
		String splitBy = ",";
		File folder = new File(folderPath);
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\madla\\Google Drive\\TDK\\Java\\Results\\results.txt"));
		String line;
		int [] array = null;
		int sum = 0;
		int  count = 0;
		ArrayList<Integer> arrlist = new ArrayList<Integer>();
		while((line = br.readLine()) != null){
			
			String[] b = line.split(splitBy);
			array = Arrays.stream(b).mapToInt(Integer::parseInt).toArray();
			arrlist.add(array[0]);
			
			sum += array[0];
			count ++;
			
			//Integer sumofNumbers = sumNumbers(array);
		
		}
		br.close();
		
		int avg = sum/count;
		System.out.println(avg);
		System.out.println(arrlist);
		
	}
}
