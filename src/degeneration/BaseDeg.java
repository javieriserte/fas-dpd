/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 * This program is distributed WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
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
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.4
 */
public class BaseDeg {
	
	char[] bases = "-ACMTWYHGRSVKDBN".toCharArray(); 
		//  four bits are used to represent degenerated bases.
		//  first bit represents 'A'.
		//  second bit represents 'C'.
		//  third bit represents 'T'.
		//  fourth bit represents 'G'.
		//  The four bits are turned into a number from 0 to 15.
		// 	this number is the position of each base in 'bases' array.
	private Map<Character,Integer> nucToIntMap; 
	static private BaseDeg mySingleton=null;
	
	// CONSTRUCTOR
	/**
	 * Creates a new instance of BaseDeg.
	 * Turns 'bases' to a Map.
	 * Having both representations for the degenerated bases is a little more efficient.
	 * Some operations are better with one representation, some other are better with the other representation. 
	 */
	private BaseDeg(){
		this.nucToIntMap = new HashMap<Character,Integer>();
		for (int x=1;x<this.bases.length;x=x+1) {
			this.nucToIntMap.put(this.bases[x], x);
		}
		this.nucToIntMap.put('-',15);
	}
	/**
	 * Creates a the BaseDeg Object in singleton.
	 * @return a BaseDeg Object.
	 */
	static public BaseDeg newBaseDeg() {
		if (BaseDeg.mySingleton==null) {
			BaseDeg.mySingleton= new BaseDeg();
		}
		return BaseDeg.mySingleton;
	}
	
	// INSTANCE METHODS
	/**
	 * Retrieve a number that represents a degenerated base.
	 */
	public int getIntFromCharbase(char myBase) {
		return this.nucToIntMap.get((Character) myBase);
	}
	/**
	 * Retrieve base char from a number.
	 */
	public char getCharbaseFromInt(int myBaseInt) {
		return this.bases[myBaseInt];
	}
	/**
	 * Combines two bases into another base.
	 * 
	 *  <blockquote>
	 *  Examples: <br>
	 *  	pileUpBase('a','a') = 'a'<br> 
	 *  	pileUpBase('a','c') = 'm'<br>
	 *  	pileUpBase('s','w') = 'n'<br>
	 *  </blockquote>>
	 *  
	 *  	
	 * @param baseUno
	 * @param baseDos
	 * @return
	 */
	public char pileUpBase(char baseUno, char baseDos) {
		return this.getCharbaseFromInt(this.getIntFromCharbase(baseUno) | this.getIntFromCharbase(baseDos)); 
	}
	
	/**
	 * This method is used to calculate the degeneration value of a base.
	 * That is how many non degenerated bases are represented by a single degenerated base.
	 * 
	 * <blockquote>
	 * Examples:<br>
	 * calculateDegValue('a') = 1
	 * calculateDegValue('m') = 2
	 * calculateDegValue('h') = 3
	 * calculateDegValue('n') = 4
	 * calculateDegValue('-') = 0
	 * 
	 * </blockquote>
	 * 
	 * @param base
	 * @return
	 */
	public int calculateDegValue(char base){
		int i = this.getIntFromCharbase(base);
		int s =0;
		
		for (int x=1;x<=8;x=2*x) {
			s = s + (i & x)/x;
		}
		return s;
		
	}
	
}
