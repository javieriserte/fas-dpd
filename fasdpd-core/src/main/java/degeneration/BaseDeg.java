package degeneration;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains tools to work with degenerated DNA sequences.
 *
 * Bases are represented by different ways, a Char value and an integer value.
 * Char representation is better for printing, showing in screen, etc. Integer
 * representation is better for operations. Four bits are used to represent each
 * degenerated base.
 *
 * <pre>
 * first bit represents 'A'.
 * second bit represents 'C'.
 * third bit represents 'T'.
 * fourth bit represents 'G'.
 * </pre>
 * The four bits are turned into a number from 0 to 15. This number is the
 * position of each base in 'bases' array.
 *
 * Also, there are two data structures to handle degenerated bases, a List and a
 * Map. The list is useful to map a Char value from an integer. The Map
 * structure is useful to map from a char to an Integer.
 *
 * Having both representations for the degenerated bases is a little more
 * efficient.
 *
 * Some operations are better with one representation, some other are better
 * with the other representation.
 *
 * This class has not internal state, so all methods are class members.
 */
public class BaseDeg {
	static char[] bases = ".ACMTWYHGRSVKDBN-".toCharArray();
	static private Map<Character,Integer> nucToIntMap =
		BaseDeg.createBaseToIntMap();

	/**
	 * Retrieves a number that represents a degenerated base.
	 *
	 * @param myBase can be a char of the array :
	 * {'-','A','C','M','T','W','Y','H','G','R','S','V','K','D','B','N','.'}
	 * @return a number from 0 to 15.
	 */
	static public int getIntFromChar(char myBase) {
		return BaseDeg.nucToIntMap.get((Character) myBase);
	}
	/**
	 * Retrieves a base char from a number
	 * @param myBaseInt is a int from 0 to 15.
	 * @example dsf
	 * @return a char representing a base in IUPAC code.
	 */
	static public char getCharFromInt(int myBaseInt) {
		return BaseDeg.bases[myBaseInt];
	}
	/**
	 * Retrieves an array of non-degenerated bases from a degenerated base. The
	 * non-degenerated bases in the array are each non-degenerated base
	 * represented by the degenerated base.<br>
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
	 * Retrieves an array of non-degenerated bases from an int value representing
	 * a degenerated base. The non-degenerated bases in the array are each
	 * non-degenerated base represented by the degenerated base.<br>
	 * Example:
	 * <pre>
	 * getCharArrayFromInt(1) = {'A'}
	 * getCharArrayFromInt(2) = {'C'}
	 * getCharArrayFromInt(9) = {'A','G'}
	 * getCharArrayFromInt(11) = {'A','C','T'}
	 * getCharArrayFromInt(15) = {'A','C','T','G'}
	 * </pre>
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
	 * Retrieves an array numbers representing non-degenerated bases from a
	 * degenerated base. The non-degenerated bases in the array are each
	 * non-degenerated base represented by the degenerated base.<br>
	 * Example:
	 * <pre>
	 * getIntArrayFromChar('A')={1}
	 * getIntArrayFromChar('C')={2}
	 * getIntArrayFromChar('R')={1,8}
	 * getIntArrayFromChar('V')={1,2,4}
	 * getIntArrayFromChar('N')={1,2,4,8}
	 * </pre>
	 * @param myBase is a <code>char</code> that represents a degenerated base.
	 * @return an array of <code>int</code> values.
	 */
	static public int[] getIntArrayFromChar(char myBase) {
		return BaseDeg.getIntArrayFromInt(
			BaseDeg.getIntFromChar(myBase)
		);
	}

	/**
	 * <pre>
	 * Retrieves an array numbers representing non-degenerated bases from a number
	 * that represents a degenerated base. The non-degenerated bases in the array
	 * are each non-degenerated base represented by the degenerated base.
	 *
	 * Example:
	 * getIntArrayFromInt(1)={1}
	 * getIntArrayFromInt(2)={2}
	 * getIntArrayFromInt(9)={1,8}
	 * getIntArrayFromInt(11)={1,2,4}
	 * getIntArrayFromInt(15)={1,2,4,8}
	 * </pre>
	 * @param myBase is a char that represents a degenerated base.
	 * @return an array of int values.
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
	 * <pre>
	 * Combines two bases into another base.
	 *  Examples:
	 *  pileUpBase('A','A') = 'A'
	 *  pileUpBase('A','C') = 'M'
	 *  pileUpBase('S','W') = 'N'
	 * </pre>
	 * @param firstBase
	 * @param secondBase
	 * @return a new <code>char</code> thar results from the combination of baseUn
	 */
	static public char pileUpBase(char firstBase, char secondBase) {
		var c1 = BaseDeg.getIntFromChar(firstBase);
		var c2 = BaseDeg.getIntFromChar(secondBase);
		return BaseDeg.getCharFromInt( c1 | c2);
	}

	/**
	 * <pre>
	 * This method is used to calculate the degeneration value of a base. That is
	 * how many non degenerated bases are represented by a single degenerated
	 * base.
	 * Examples:
	 * getDegValueFromChar('A') = 1
	 * getDegValueFromChar('M') = 2
	 * getDegValueFromChar('H') = 3
	 * getDegValueFromChar('N') = 4
	 * getDegValueFromChar('-') = 0
	 * </pre>
	 * @param base is a char representing a degenerated base.
	 * @return the degeneration value for a particular base.
	 */
	static public int getDegValueFromChar(char base){
		return BaseDeg.getDegValueFromInt(BaseDeg.getIntFromChar(base));
	}

	/**
	 * <pre>
	 * This method is used to calculate the degeneration value of a base. That is
	 * how many non degenerated bases are represented by a single degenerated
	 * base.
	 *
	 * Examples:
	 * getDegValueFromInt(1) = 1  # 1 represents A
	 * getDegValueFromInt(2) = 1  # 2 represents C
	 * getDegValueFromInt(3) = 2  # 3 represens M
	 * getDegValueFromInt(4) = 1  # 4 represents T
	 * getDegValueFromInt(15) = 4 # 15 represents N
	 * </pre>
	 *
	 * @param base is a int representing a degenerated base.
	 * @return the degeneration value for a particular base.
	 */
	static public int getDegValueFromInt(int base) {
		int s =0;
		for (int x=1;x<=8;x=2*x) {
			s = s + (base & x)/x;
		}
		return s;
	}

	/**
	 * <pre>
	 * This method is used to calculate the degeneration value of a string
	 * representing a degenerated sequence.
	 * Examples:
	 * getDegValueFromInt(1) = 1  # 1 represents A
	 * getDegValueFromInt(2) = 1  # 2 represents C
	 * getDegValueFromInt(3) = 2  # 3 represens M
	 * getDegValueFromInt(4) = 1  # 4 represents T
	 * getDegValueFromInt(15) = 4 # 15 represents N
	 * </pre>
	 * @param seq is a String
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
	 * Given a nondegenerated base represented as a integer and a second
	 * degenerated base represented as a char, this method returns true if the
	 * second one contains the first. Here the integer value for represent the
	 * base is different from the used in other methods.
	 * <pre>
	 * baseValue=0 # for A.
	 * baseValue=1 # for C.
	 * baseValue=2 # for T.
	 * baseValue=3 # for G.
	 * </pre>
	 * @param baseValue a int representing a non-degenerated base.
	 * @param myDegBase a char representing possibly a degenerated base.
	 * @return true if myDegBase contains baseValue, false otherwise.
	 */
	static public boolean containsBaseIntInChar(int baseValue, char myDegBase) {
		int degBase = BaseDeg.getIntFromChar(myDegBase);
		return (degBase & ((int)Math.pow(2,baseValue)))>0;
	}
	/**
	 * Given a non-degenerated value, represented as a char.
	 * This method returns true if the degenerated base contains it.
	 *
	 * @param baseValue a char representing a non-degenerated value:
	 * <pre>
	 * baseValue= 'A'. or
	 * baseValue= 'C'. or
	 * baseValue= 'T'. or
	 * baseValue= 'G'.
	 * </pre>
	 * @param myDegBase a <code>char</code> representing a degenerated base.
	 * @return true if myDegBase contains baseValue, false otherwise.
	 */
	static public boolean containsBaseCharInChar(char baseValue, char myDegBase) {
		int bValue  = BaseDeg.getIntFromChar(baseValue);
		return BaseDeg.containsBaseIntInChar(bValue, myDegBase);
	}

	/**
	 * Creates a Hash to store bases.
	 */
	private static HashMap<Character,Integer> createBaseToIntMap() {
		HashMap<Character,Integer> nucToIntMap = new HashMap<Character,Integer>();
		for (int x=0;x<BaseDeg.bases.length;x=x+1) {
			nucToIntMap.put(BaseDeg.bases[x], x);
		}
		nucToIntMap.put('-', 15);
		return nucToIntMap;
	}
}
