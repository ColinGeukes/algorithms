/** Class created at created at 24 sep. 2016 */
package algorithms;

/** @author Colin Geukes */

/* TODO: 
 * - Add decoding for Encode.String
*/

public class RunLengthEncoding {
	
	/**
	 * Encodes a given string using Run-Length Encoding 8-bit.
	 * @param message - The string you want to encode to RLE8
	 * @param encoding - The type of encoding you want to use
	 * @return a string that is encoded into RLE8
	 */
	public static String encodeRLE8(String message, Encode encoding){
		return encodeRLE8(message, encoding, false, false);
	}
	
	/**
	 * Encodes a given string using Run-Length Encoding 8-bit.
	 * @param message - The string you want to encode to RLE8
	 * @param encoding - The type of encoding you want to use
	 * @param spaced - If you want the encoded RLE8 string to have spaces
	 * @param lined - If you want the encoded RLE8 string to use multiple lines
	 * @return a string that is encoded into RLE8
	 */
	public static String encodeRLE8(String message, Encode encoding, boolean spaced, boolean lined){
		String strOut = "";
		
		int position = 0;
		int size = 0;
		
		while(position < message.length()){
			Character c = message.charAt(position);
			size = 1;
			position++;
			
			//Because we don't use negative values, we can set the MAX_VALUE to 255 instead of 127.
			while(size < (Byte.MAX_VALUE+1)*2-1 && position < message.length()){
				if(message.charAt(position) != c)
					break;
				size++;
				position++;
			}
			
			//Different encode types
			switch(encoding){
			case String: 
				strOut += Integer.toString(size) + c + (spaced&&!lined?" ":"") + (lined?"\n":"");
				break;
			case Binary:
				strOut +=	Integer.toBinaryString(0x100 | size).substring(1) + (spaced?" ":"") + Integer.toBinaryString(0x100 | c).substring(1) + (spaced&&!lined?" ":"") +  (lined?"\n":"");
				break;
			case HexaDecimal:
				strOut +=	Integer.toHexString(0x100 | size).substring(1) + (spaced?" ":"") + Integer.toHexString(0x100 | c).substring(1) + (spaced&&!lined?" ":"") + (lined?"\n":"");
				break;
			}
		}
		
		return strOut.substring(0, strOut.length() - (lined||spaced? 1 : 0));
	}
	
	/**
	 * Method that decodes an encoded string using Run-Length Encoding 8-bit
	 * @param encoded - The string that is encoded and you that you want to decode. String can contain whitespace.
	 * @param encoding - The type in which the encoded string is encoded.
	 * @return a decoded string.
	 */
	public static String decodeRLE8(String encoded, Encode encoding){
		//Remove all whitespace from the encoded string.
		encoded = encoded.replaceAll("\\s+","");
		String decoded = "";
		
		switch(encoding){
		case String:
			break;
		case Binary:
		case HexaDecimal:
			//Length of each segment, with binary we have 8*2 bytes and with hexadecimal we have 2*2 hex.
			int length = (encoding == Encode.Binary)? 8 : 2;
			int radix = (encoding == Encode.Binary)? 2 : 16;
			int parts = 2;
			int segments = encoded.length() / (length * parts);
			
			
			//Get the segment 
			for(int seg = 0; seg < segments; seg++){
				
				//Get the binary strings
				String charString = encoded.substring((seg*parts + 1) * length, (seg * parts + 2) * length);
				String amountString = encoded.substring((seg*parts) * length, (seg * parts + 1) * length);
				
				//Get the corresponding bytes
				Character c = (char)Integer.parseInt(charString, radix);
				int amount = Integer.parseInt(amountString, radix) & 0xFF;
				
				//Add the character unsignedAmount-times to the decoded string.
				for(int i = 0; i < amount; i++)
					decoded += c;
			}
		}

		return decoded;
	}
	
	/**
	 * The style you want to encode your messages in.
	 */
	public static enum Encode{
		String, Binary, HexaDecimal
	}
}
