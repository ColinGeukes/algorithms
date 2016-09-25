/**Class created at created at 24 sep. 2016 @ 23:50:35
 * 
 */
package algorithms;

import java.util.Scanner;

/**
 * @author Colin Geukes
 */
public class RunLenghtEncoding {
	private static Scanner scanner;
	private static String message;
	
	public static void main(String args[]){
		scanner = new Scanner(System.in);
		
		System.out.print("Please, enter a string you want to be \"Run-Length\"-encoded\n\tstring:");
		message = scanner.nextLine();	
		scanner.close();
				
		//All possible encodes.
		Encode encode = Encode.String;
		boolean spaced = false;
		boolean lined = false;
		for(int i = 0; i < 12; i++){
			//spaced
			if(i < 4)
				encode = Encode.String;
			else if(i < 8)
				encode = Encode.Byte;
			else
				encode = Encode.HexaDecimal;
			
			//Encode types
			if(i % 2 == 0)
				spaced = false;
			else 
				spaced = true;

			//lined
			if((i + 1) % 4 == 0 || (i + 2) % 4 == 0)
				lined = true;
			else 
				lined = false;
			
			//print the encode
			System.out.print("\n----[Encode: Encode=" + encode + ", spaced=" + spaced + ", lined=" + lined + "]----\n" + encodeRLE8(message, encode, spaced, lined) + "\n");
		}
	}
	
	/**
	 * Encodes a given string into RLE8
	 * @param strIn - The string you want to encode to RLE8
	 * @param encoding - The type of encoding you want to use
	 * @param spaced - If you want the encoded RLE8 string to have spaces
	 * @param lined - If you want the encoded RLE8 string to use multiple lines
	 * @return a string that is encoded into RLE8
	 */
	private static String encodeRLE8(String strIn, Encode encoding, boolean spaced, boolean lined){
		String strOut = "";
		
		int position = 0;
		int size = 0;
		
		while(position < strIn.length()){
			Character c = strIn.charAt(position);
			size = 1;
			position++;
			
			//Because we don't use negative values, we can set the MAX_VALUE to 255 instead of 127.
			while(size < (Byte.MAX_VALUE+1)*2-1 && position < strIn.length()){
				if(strIn.charAt(position) != c)
					break;
				size++;
				position++;
			}
			
			//Different encode types
			switch(encoding){
				default:
				case String: 
					strOut += Integer.toString(size) + c + (spaced&&!lined?" ":"") + (lined?"\n":"");
					break;
				case Byte:
					strOut +=	Integer.toBinaryString(0x1000 | size).substring(1) + (spaced?" ":"") + Integer.toBinaryString(0x1000 | c).substring(1) + (spaced&&!lined?" ":"") +  (lined?"\n":"");
					break;
				case HexaDecimal:
					strOut +=	Integer.toHexString(0x100 | size).substring(1) + (spaced?" ":"") + Integer.toHexString(0x100 | c).substring(1) + (spaced&&!lined?" ":"") + (lined?"\n":"");
					break;
			}
		}
		
		return strOut.substring(0, strOut.length() - (lined||spaced? 1 : 0));
	}
	
	/**
	 * The way you want to encode your messages
	 */
	private static enum Encode{
		String, Byte, HexaDecimal
	}
}
