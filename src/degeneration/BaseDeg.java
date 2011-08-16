/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE 
 * PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL 
 * NECESSARY SERVICING, REPAIR OR CORRECTION.
 
 * IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT 
 * HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS PERMITTED ABOVE, 
 * BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL
 * DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED 
 * TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD 
 * PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH 
 * HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package degeneration;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains tools to work with degenerated DNA sequences.
 * 
 * Bases are represented by different ways, a Char value and an integer value. Char representation is better for printing, showing in screen, etc. 
 * Integer representation is better for operations. Four bits are used to represent each degenerated base.
 * 		  first bit represents 'A'.
 * 		  second bit represents 'C'.
 * 		  third bit represents 'T'.
 * 		  fourth bit represents 'G'.
 * 		  The four bits are turned into a number from 0 to 15. This number is the position of each base in 'bases' array.
 *  
 * Also, thereas two data structures to handle degenerated bases, a List and a Map. The list is useful to map a Char value from an integer.
 * The Map structure is useful to map from a char to an Integer.
 *   
 * Having both representations for the degenerated bases is a little more efficient.
 * 
 * Some operations are better with one representation, some other are better with the other representation.
 * 
 * This class has not internal state, so all methods are class members.
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class BaseDeg {
	
	static char[] bases = "-ACMTWYHGRSVKDBN".toCharArray(); 
	static private Map<Character,Integer> nucToIntMap = BaseDeg.createBaseToIntMap();
	
//	static private BaseDeg mySingleton=null;
	
	private static HashMap<Character,Integer> createBaseToIntMap() {
		HashMap<Character,Integer> nucToIntMap = new HashMap<Character,Integer>();
		for (int x=1;x<BaseDeg.bases.length;x=x+1) {
			nucToIntMap.put(BaseDeg.bases[x], x);
		}
		nucToIntMap.put('-',15);
		return nucToIntMap;
	}

	
	
//		// CONSTRUCTOR
//	/**
//	 * Creates a new instance of BaseDeg.
//	 */
//	private BaseDeg(){
//		BaseDeg.nucToIntMap = new HashMap<Character,Integer>();
//		for (int x=1;x<BaseDeg.bases.length;x=x+1) {
//			BaseDeg.nucToIntMap.put(BaseDeg.bases[x], x);
//		}
//		BaseDeg.nucToIntMap.put('-',15);
//	}
	
	
//	/**
//	 * Creates a the BaseDeg Object in singleton.
//	 * @return a BaseDeg Object.
//	 */
//	static public BaseDeg newBaseDeg() {
//		if (BaseDeg.mySingleton==null) {
//			BaseDeg.mySingleton= new BaseDeg();
//		}
//		return BaseDeg.mySingleton;
//	}
	
	// PUBLIC INTERFACE METHODS
	/**
	 * Retrieve a number that represents a degenerated base.
	 */
	static public int getIntFromChar(char myBase) {
		return BaseDeg.nucToIntMap.get((Character) myBase);
	}
	/**
	 * Retrieve base char from a number.
	 */
	static public char getCharFromInt(int myBaseInt) {
		return BaseDeg.bases[myBaseInt];
	}
	/**
	 * Retrieve an array of non-degenerated chars from a degenetared char 
	 */
	static public char[] getCharArrayFromChar(char myBase) {
		return BaseDeg.getCharArrayFromInt(BaseDeg.getIntFromChar(myBase));
	}
	
	/**
	 * Retrieve an array of non-degenerated chars from a degenerated int 
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
	 * Retrieve an array of non-degenerated ints from a degenerated char 
	 */
	static public int[] getIntArrayFromChar(char myBase) {
		
		return BaseDeg.getIntArrayFromInt(BaseDeg.getIntFromChar(myBase));
	}

	/**
	 * Retrieve an array of non-degenerated ints from a degenerated int 
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
	 * @param baseUno
	 * @param baseDos
	 * @return
	 */
	static public char pileUpBase(char baseUno, char baseDos) {
		return BaseDeg.getCharFromInt(BaseDeg.getIntFromChar(baseUno) | BaseDeg.getIntFromChar(baseDos)); 
	}
	
	/**
	 * This method is used to calculate the degeneration value of a base.
	 * That is how many non degenerated bases are represented by a single degenerated base.
	 * 
	 * <blockquote>
	 * Examples:<br>
	 * getDegValueFromChar('A') = 1
	 * getDegValueFromChar('M') = 2
	 * getDegValueFromChar('H') = 3
	 * getDegValueFromChar('N') = 4
	 * getDegValueFromChar('-') = 0
	 * 
	 * </blockquote>
	 * 
	 * @param base
	 * @return
	 */
	static public int getDegValueFromChar(char base){
		return BaseDeg.getDegValueFromInt(BaseDeg.getIntFromChar(base));
	}

	/**
	 * This method is used to calculate the degeneration value of a base.
	 * That is how many non degenerated bases are represented by a single degenerated base.
	 * 
	 * <blockquote>
	 * Examples:<br>
	 * getDegValueFromInt(1) = 1  ' 1 represnets A
	 * getDegValueFromInt(2) = 1  ' 2 represnets C
	 * getDegValueFromInt(3) = 2  ' 3 represnets M
	 * getDegValueFromInt(4) = 1  ' 4 represnets T
	 * getDegValueFromInt(15) = 4 ' 15 represnets N
	 * 
	 * </blockquote>
	 * 
	 * @param base
	 * @return
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
	 * 
	 * @param base
	 * @return
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
	 * 
	 * baseValue=0, for A.
	 * baseValue=1, for C. 
	 * baseValue=2, for T.
	 * baseValue=3, for G.
	 * 
	 * @param baseValue
	 * @return
	 */
	static public boolean containsBaseIntInChar(int baseValue, char myDegBase) {
		int degBase = BaseDeg.getIntFromChar(myDegBase);
		return (degBase & ((int)Math.pow(2,baseValue)))>0;
	}
	
	/**
	 * Given a nondegenerated value, represented as a char.
	 * This method returns true if the BaseDeg contains it.
	 * 
	 * baseValue= A.
	 * baseValue= C. 
	 * baseValue= T.
	 * baseValue= G.
	 * 
	 * @param baseValue
	 * @return
	 */
	static public boolean containsBaseCharInChar(char baseValue, char myDegBase) {
		
		int bValue  = BaseDeg.getIntFromChar(baseValue);
		return BaseDeg.containsBaseIntInChar(bValue, myDegBase);
		
	}	
	
	
	
		
	
}
