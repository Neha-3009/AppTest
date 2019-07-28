package automationUtility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
	public static void main (String[] a) {
		getContent("D:\\Selenium\\computerDatabase\\data\\CreatComputerData.csv");
	}
	
	public static String[][] getContent(String filename) {
		List<String[]> data = new ArrayList<String[]>();
		String[][] dataTable=null;
		String testRow;
		try {
			// Open and read the file
			BufferedReader br = new BufferedReader(new FileReader(filename));
			// Read data as long as it's not empty
			// Parse the data by comma using .split() method
			// Place into a temporary array, then add to List 
			while ((testRow = br.readLine()) != null) {
				String[] line = testRow.split(",");
				data.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found " + filename);
		} catch (IOException e) {
			System.out.println("ERROR: Could not read " + filename);
		}
		
		int numRows=data.size();
		int numCols=data.get(0).length;
		dataTable = new String[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			String[] dataRow = data.get(i);
			for (int j = 0; j < numCols; j++) {
				if(dataRow.length>j) {
					String dataCol = dataRow[j];
					dataTable[i][j] = dataCol.toString();
				}
				
			}
		}
		return dataTable;
	}
	
}
