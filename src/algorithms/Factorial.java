/** Factorial.java in algorithms created 25 sep. 2016 */
package algorithms;

/** @author Colin Geukes */
public class Factorial {

	/**
	 * Method that returns the factorial value of a given value.
	 * @param n - the number you want to get the factorial value from.
	 * @return the factorial value of n.
	 * @throws IllegalArgumentException if n is a negative value.
	 */
	public static int factorial(int n){
		if(n < 0)	throw new IllegalArgumentException("int n needs cannot be a negative value.");
		if(n <= 1)	return 1;
		return n * factorial(n-1);
	}
}
