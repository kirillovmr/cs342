import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Statistics {
	
	private double values[];
	
	Statistics(String filename, int size){
		values = new double[size];
		readInValues(filename);
		Arrays.parallelSort(values);
		printArray();
	}
	
	public void printMean() {
		System.out.println("This is the mean: " + StatFormulas.mean(values));
	}
	
	public void printMedian() {
		System.out.println("This is the median: " + StatFormulas.median(values));
	}
	
	public void printSTD() {
		System.out.println("This is the STD: " + StatFormulas.std(values));
	}
	
	private void readInValues(String filename) {
		
		
		try {
			File f = new File("src/main/resources/"+filename);
			
			Scanner s = new Scanner(f);
			
			int i = 0;
			while(s.hasNextDouble()) {
				values[i] = s.nextDouble();
				i++;
			}
			
			s.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void printArray() {
		
		for(int i = 0; i < values.length; i++) {
			System.out.println(values[i]+ " ");
		}
	}
	
	public double[] getValues() {
		return values;
	}
	

}
