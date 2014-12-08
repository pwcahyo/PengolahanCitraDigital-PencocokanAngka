package DetectNumber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class WriteFile {
	
	public void writeArrayToFile(int[][] dataInput, String path) {
		PrintStream ps;
		try {
			ps = new PrintStream(new FileOutputStream(path));
			for (int row = 0; row < dataInput.length; row++) {
				for (int col = 0; col < dataInput.length; col++) {
					int s = dataInput[row][col];
					ps.print(s);
				}
				ps.println();
			}
			ps.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String[][] readFile(String path) {
		Scanner inputStream = null;
		System.out.println("Data uji \n\n");
		try
		{
		  inputStream = new Scanner(new File(path));
		}
		catch(FileNotFoundException e)
		{
		  System.out.println("Error opening the file " + path);
		  System.exit(0);
		}
		
		String [][] data = new String[10][10];
		
		for (int row = 0; row < 10; row++) {
			for (int cols = 0; cols < 10; cols++) {
			    String line = inputStream.nextLine();
			    //System.out.println(line);
			    data[row][cols] = line;
			}
		}
		inputStream.close();
		
		return data;
	}
}