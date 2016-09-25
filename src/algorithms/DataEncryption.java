/** DataEncryption.java in algorithms created 25 sep. 2016 */
package algorithms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/** @author Colin Geukes */
public class DataEncryption {
	
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Date Encryption:\n\tMessage: ");
		String message = RunLengthEncoding.encodeRLE8(scanner.nextLine(), RunLengthEncoding.Encode.Binary);
		System.out.print( "\tKey: ");
		String key = scanner.nextLine();
		scanner.close();
		System.out.print("\n" + message);
		System.out.print("\n" + key);

		message = encrypt(message, key);
		System.out.println("\n" + message);
		
		System.out.println("\n" + RunLengthEncoding.decodeRLE8(message, RunLengthEncoding.Encode.Binary));
		
		try {
			//Clean the test.txt
			FileOutputStream writer = new FileOutputStream("test.txt");
			writer.write(("").getBytes());
			writer.close();

		    Files.write(Paths.get("test.txt"), BinaryToText(message).getBytes(), StandardOpenOption.APPEND);
		    
			message = encrypt(TextToBinary(Files.readAllLines(Paths.get("test.txt")).get(0)), key);
			System.out.println(message);
		}catch (IOException e) {}
		

		System.out.println("\n" + RunLengthEncoding.decodeRLE8(message, RunLengthEncoding.Encode.Binary));
	}
	
	/**
	 * Method to encrypt a string or remove an encryption from a string.
	 * @param input - string you want to be encrypted
	 * @param key - a key used to encrypt the input string.
	 * @return an encrypted string or string which has its encryption removed.
	 */
	public static String encrypt(String input, String key){
		//Remove all the whitespace from the input string
		input = input.replaceAll("\\s+",""); 
		
		//Transform the key to binary
		key = TextToBinary(key);
		String output = "";
		
		for(int i = 0; i < input.length(); i++){
			//Get the character at i of the input and key.
			byte i_input = (byte)input.charAt(i);
			byte i_key = (byte)key.charAt(i % key.length());
			
			//Do a XOR operation.
			if(i_input != i_key){
				output += '1';
			} else {
				output += '0';
			}
		}
		return output;
	}
	
	/**
	 * Method to change a normal string to a binary string.
	 * @param input - normal string.
	 * @return a binary string.
	 */
	public static String TextToBinary(String input){
		String output = "";
		for(int i = 0; i < input.length(); i++){
			output += Integer.toBinaryString(0x100 | input.charAt(i)).substring(1);
		}
		return output;
	}
	
	public static String BinaryToText(String input){
		String output = "";
		for(int i = 0; i < input.length()/8; i++){
			int character = Integer.parseInt(input.substring(i *8, (i + 1) * 8), 2);
			output += (char)character;
		}
		return output;
	}
}
