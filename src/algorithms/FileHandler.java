/** FileStoring.java in algorithms created 26 Sep 2016 */
package algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/** @author Colin Geukes */
public class FileHandler {

	public static void createFile(String path){
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.close();
		} catch (FileNotFoundException|UnsupportedEncodingException e) {e.printStackTrace();}
	}
	
	public static void writeFile(String path, String message){
		 try {
			Files.write(Paths.get(path), message.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static void emtpyFile(String path){
		try {
			FileOutputStream writer = new FileOutputStream(path);
			writer.write(("").getBytes());
			writer.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static String getStrings(String path) throws FileNotFoundException, NullPointerException{
		String lines = "";

		Scanner scanner = new Scanner(new File(Paths.get(path).toUri()));
		while(scanner.hasNextLine())
			lines+=scanner.nextLine();
		scanner.close();
		
		return lines;
	}
}
