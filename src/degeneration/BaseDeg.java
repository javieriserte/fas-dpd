package degeneration;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains tools to work with degenerated DNA sequences.
 * 
 * Bases are represented by different ways, a Char value and an integer value. Char representation is better for printing, showing in screen, etc. 
 * Integer representation is better for operations. Four bits are used to represent each degenerated base.
 * <blockquote>
 * <b>first bit </b>represents 'A'.<br>
 * <b>second bit </b>represents 'C'.<br>
 * <b>third bit </b>represents 'T'.<br>
 * <b>fourth bit </b>represents 'G'.<br>
 * The four bits are turned into a number from 0 to 15. This number is the position of each base in 'bases' array.<br>
 * </blockquote>
 *  
 * Also, there are two data structures to handle degenerated bases, a List and a Map. The list is useful to map a Char value from an integer.
 * The Map structure is useful to map from a char to an Integer.<br>
 *   
 * Having both representations for the degenerated bases is a little more efficient.<br>
 * 
 * Some operations are better with one representation, some other are better with the other representation.<br>
 * 
 * This class has not internal state, so all methods are class members.<br>
 */
public class BaseDeg {
	
	// Private Class Variables
	static char[] bases = "-ACMTWYHGRSVKDBN".toCharArray(); 
	static private Map<Character,Integer> nucToIntMap = BaseDeg.createBaseToIntMap();
	
	
	// PUBLIC INTERFACE METHODS
	/**
	 * Retrieves a number that represents a degenerated base.<br>
	 * Example:
	 * <blockquote><tt>
	 *         getIntFromChar('A') = 1<br>
	 *         getIntFromChar('C') = 2<br>
	 *         getIntFromChar('T') = 4<br>
	 *         getIntFromChar('G') = 8<br>
	 *         getIntFromChar('-') = 0<br>
	 *         getIntFromChar('N') = 15<br>
	 * </tt></blockquote>
	 * 
	 * @param myBase can be a <code>char</code> of the array : {'-','A','C','M','T','W','Y','H','G','R','S','V','K','D','B','N'}<br>
	 * @return a number from 0 to 15.
	 */
	static public int getIntFromChar(char myBase) {
		return BaseDeg.nucToIntMap.get((Character) myBase);
	}
	/**
	 * Retrieves a base char from a number.<br>
	 * Example:
	 * <blockquote><tt>
	 *         getCharFromInt(1) =('A')<br>
	 *         getCharFromInt(2) =('C')<br>
	 *         getCharFromInt(4) =('T')<br>
	 *         getCharFromInt(8) =('G')<br>
	 *         getCharFromInt(0) =('-')<br>
	 *         getCharFromInt(15)=('N')<br>
	 * </tt></blockquote> 
	 * @param myBaseInt is a <code>int</code> from 0 to 15.
	 * @return a <code>char</code> representing a base in IUPAC code.
	 */
	static public char getCharFromInt(int myBaseInt) {
		return BaseDeg.bases[myBaseInt];
	}
	/**
	 * Retrieves an array of non-degenerated bases from a degenerated base.
	 * The non-degenerated bases in the array are each non-degenerated base represented by the degenerated base.<br>
	 * Example:
	 * <blockquote><tt>
	 *         getCharArrayFromChar('A')={'A'}<br>
	 *         getCharArrayFromChar('C')={'C'}<br>
	 *         getCharArrayFromChar('R')={'A','G'}<br>
	 *         getCharArrayFromChar('V')={'A','C','T'}<br>
	 *         getCharArrayFromChar('N')={'A','C','T','G'}}<br>
	 * </tt></blockquote>
	 * @param myBase is <code>char</code> that represents a degenerated base.
	 * @return an array of bases. 
	 */
	static public char[] getCharArrayFromChar(char myBase) {
		return BaseDeg.getCharArrayFromInt(BaseDeg.getIntFromChar(myBase));
	}
	/**
	 * Retrieves an array of non-degenerated bases from an int value representing a degenerated base.
	 * The non-degenerated bases in the array are each non-degenerated base represented by the degenerated base.<br>
	 * Example:
	 * <blockquote><tt>
	 *         getCharArrayFromInt(1)={'A'}<br>
	 *         getCharArrayFromInt(2)={'C'}<br>
	 *         getCharArrayFromInt(9)={'A','G'}<br>
	 *         getCharArrayFromInt(11)={'A','C','T'}<br>
	 *         getCharArrayFromInt(15)={'A','C','T','G'}<br>
	 * </tt></blockquote>
	 * @param myBaseInt is an <code>int</code> that represents a degenerated base.
	 * @return an array of bases.
	 */
	static public char[] getCharArrayFromInt(int myBaseInt) {
		int degValue = BaseDeg.getDegValueFromInt(myBaseInt);
		char[] result = new char[degValue];
		int i=0;
		for (int x=1;x<=8;x=2*x) {
			if ((myBaseInt & x)!=0) result[i++] = BaseDeg.getCharFromInt(x);
		}
		return result;
	}
	/**
	 * Retrieves an array numbers representing non-degenerated bases from a degenerated base.
	 * The non-degenerated bases in the array are each non-degenerated base represented by the degenerated base.<br>
	 * Example:
	 * <blockquote><tt>
	 *         getIntArrayFromChar('A')={1}<br>
	 *         getIntArrayFromChar('C')={2}<br>
	 *         getIntArrayFromChar('R')={1,8}<br>
	 *         getIntArrayFromChar('V')={1,2,4}<br>
	 *         getIntArrayFromChar('N')={1,2,4,8}<br>
	 * </tt></blockquote>
	 * @param myBase is a <code>char</code> that represents a degenerated base.
	 * @return an array of <code>int</code> values.
	 */
	static public int[] getIntArrayFromChar(char myBase) {
		
		return BaseDeg.getIntArrayFromInt(BaseDeg.getIntFromChar(myBase));
	}

	/**
	 * Retrieves an array numbers representing non-degenerated bases from a number that represents a degenerated base.
	 * The non-degenerated bases in the array are each non-degenerated base represented by the degenerated base.<br>
	 * Example:
	 * <blockquote><tt>
	 *         getIntArrayFromInt(1)={1}<br>
	 *         getIntArrayFromInt(2)={2}<br>
	 *         getIntArrayFromInt(9)={1,8}<br>
	 *         getIntArrayFromInt(11)={1,2,4}<br>
	 *         getIntArrayFromInt(15)={1,2,4,8}<br>
	 * </tt></blockquote>
	 * @param myBase is a <code>char</code> that represents a degenerated base.
	 * @return an array of <code>int</code> values.
	 */
	static public int[] getIntArrayFromInt(int myBase) {
		
		int degValue = BaseDeg.getDegValueFromInt(myBase);
		int[] result = new int[degValue];
		int i=0;
		for (int x=1;x<=8;x=2*x) {
			if ((myBase & x)!=0) result[i++] = x;
		}
		return result;
	}
	
	/**
	 * Combines two bases into another base.
	 * 
	 *  <blockquote>
	 *  Examples: <br>
	 *  	pileUpBase('A','A') = 'A'<br> 
	 *  	pileUpBase('A','C') = 'M'<br>
	 *  	pileUpBase('S','W') = 'N'<br>
	 *  </blockquote>>
	 *  
	 *  	
	 * @param firstBase
	 * @param secondBase
	 * @return a new <code>char</code> thar results from the combination of baseUn
	 */
	static public char pileUpBase(char firstBase, char secondBase) {
		return BaseDeg.getCharFromInt(BaseDeg.getIntFromChar(firstBase) | BaseDeg.getIntFromChar(secondBase)); 
	}
	
	/**
	 * This method is used to calculate the degeneration value of a base.
	 * That is how many non degenerated bases are represented by a single degenerated base.
	 * 
	 * <blockquote><tt>
	 * Examples:<br>
	 * getDegValueFromChar('A') = 1<br>
	 * getDegValueFromChar('M') = 2<br>
	 * getDegValueFromChar('H') = 3<br>
	 * getDegValueFromChar('N') = 4<br>
	 * getDegValueFromChar('-') = 0<br>
	 * </tt></blockquote>
	 * 
	 * @param base is a <code>char</code> representing a degenerated base.
	 * @return the degeneration value for a particular base.
	 */
	static public int getDegValueFromChar(char base){
		return BaseDeg.getDegValueFromInt(BaseDeg.getIntFromChar(base));
	}

	/**
	 * This method is used to calculate the degeneration value of a base.
	 * That is how many non degenerated bases are represented by a single degenerated base.
	 * 
	 * <blockquote><tt>
	 * Examples:<br>
	 * getDegValueFromInt(1) = 1  ' 1 represents A<br>
	 * getDegValueFromInt(2) = 1  ' 2 represents C<br>
	 * getDegValueFromInt(3) = 2  ' 3 represens M<br>
	 * getDegValueFromInt(4) = 1  ' 4 represents T<br>
	 * getDegValueFromInt(15) = 4 ' 15 represents N<br>
	 * </tt></blockquote>
	 * 
	 * @param base is a <code>int</code> representing a degenerated base.
	 * @return the degeneration value for a particular base.
	 */
	static public int getDegValueFromInt(int base){
		int s =0;
		for (int x=1;x<=8;x=2*x) {
			s = s + (base & x)/x;
		}
		return s;
	}
	
	/**
	 * This method is used to calculate the degeneration value of a string representing a degenerated sequence.
	 * <blockquote><tt>
	 * Examples:<br>
	 * getDegValueFromInt(1) = 1  ' 1 represents A<br>
	 * getDegValueFromInt(2) = 1  ' 2 represents C<br>
	 * getDegValueFromInt(3) = 2  ' 3 represens M<br>
	 * getDegValueFromInt(4) = 1  ' 4 represents T<br>
	 * getDegValueFromInt(15) = 4 ' 15 represents N<br>
	 * </tt></blockquote>
	 * @param seq is a <code>String</code>
	 * @return the degeneration value for a sequence.
	 */
	static public int getDegValueFromString(String seq){
		
		int result=1;

		for (char c : seq.toUpperCase().toCharArray()) {
			result = result * BaseDeg.getDegValueFromChar(c);
		}
		return result;
	}

	
	/**
	 * Given a nondegenerated base represented as a integer and a second degenerated base represented as a char, 
	 * this method returns true if the second one contains the first.
	 * Here the integer value for represent the base is different from the used in other methods.
	 * <blockquote><tt>
	 * baseValue=0, for A.<br>
	 * baseValue=1, for C.<br> 
	 * baseValue=2, for T.<br>
	 * baseValue=3, for G.<br>
	 * </tt></blockquote>
	 * @param baseValue
	 * @return
	 */
	static public boolean containsBaseIntInChar(int baseValue, char myDegBase) {
		int degBase = BaseDeg.getIntFromChar(myDegBase);
		return (degBase & ((int)Math.pow(2,baseValue)))>0;
	}
	/**
	 * Given a non-degenerated value, represented as a char.
	 * This method returns true if the degenerated base contains it.
	 * 
	 * @param baseValue a <code>char</code> representing a non-degenerated value:<br>
 	 * <blockquote><tt>
	 * baseValue= 'A'. or <br>
	 * baseValue= 'C'. or <br> 
	 * baseValue= 'T'. or <br>
	 * baseValue= 'G'.<br>
	 * </tt></blockquote>
	 * @param myDegBase a <code>char</code> representing a degenerated base.
	 * @return 
	 * <code>true</code> if <code>myDegBase</code> contains <code>baseValue</code><br>
	 * <code>false</code> otherwise.
	 */
	static public boolean containsBaseCharInChar(char baseValue, char myDegBase) {
		
		int bValue  = BaseDeg.getIntFromChar(baseValue);
		return BaseDeg.containsBaseIntInChar(bValue, myDegBase);
		
	}	
	
	// Private Methods
	/**
	 * Creates a Hash to store bases.
	 */
	private static HashMap<Character,Integer> createBaseToIntMap() {
		HashMap<Character,Integer> nucToIntMap = new HashMap<Character,Integer>();
		for (int x=1;x<BaseDeg.bases.length;x=x+1) {
			nucToIntMap.put(BaseDeg.bases[x], x);
		}
		nucToIntMap.put('-',15);
		return nucToIntMap;
	}

		
	
}
