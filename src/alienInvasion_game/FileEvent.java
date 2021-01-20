package alienInvasion_game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileEvent {

	public static void createFile(String filePath) {
		try {
			File myObj = new File(filePath);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void writeToFile(String filePath, String content) {
		try (FileWriter fileWriter = new FileWriter(filePath)) {
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) {
			// Cxception handling
		}

	}

	public static ArrayList<String> readLines(String fileName) {
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while(line != null){
				toReturn.add(line);
				line = br.readLine();
			}
			
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public static void appendToFile(String fileName, String appendMessage) {
		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(appendMessage);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
