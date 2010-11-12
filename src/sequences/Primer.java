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
 */package sequences;

import java.util.List;
import java.util.Vector;

/**
 * Class to represent a Oligonucleotide that is Primer for PCR.
 * Extend of DNAseq.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.2
 */
public class Primer extends DNASeq implements Comparable<Primer>{
	// INSTANCE VARIABLES
	private float score;
	private int start;
	private int end;
	private boolean directStrand;

	// CONSTRUCTOR	
	/**
	 * Creates a new primer from a sequence and a description.
	 * Also requires a start, end and directStrand values that 
	 * references to the sequence from where the primer was designed.
	 * 
	 */
	public Primer(String sequence, String description,int start,int end, boolean directStrand) {
		super(sequence,description);
		this.setStart(start);
		this.setEnd(end);
		this.setDirectStrand(directStrand);
	}


	// INSTANCE METHODS 
	/**
	 * Implementation of comparable interface.
	 * Primers are comparable by a score.
	 */
	@Override
	public int compareTo(Primer anotherPrimer) {
		return ((Float) this.getScore()).compareTo(anotherPrimer.getScore());
	}
	
	/**
	 * Looks for complementary with other primer. 
	 * @return a list of subsequences that are complementary.
	 * @since 1.1.2
	 */
	public List<String> searchNonGappedComplementation(Primer otherPrimer, int largerthan) {
		char[] a = this.getSequence().toCharArray();
		char[] b = otherPrimer.getComplementary().getSequence().toCharArray();
		int[][] m = new int[this.getLength()+1][otherPrimer.getLength()+1];
		List<String> result = new Vector<String>();
		
		
		// STEP ZERO : INITIALIZE
		for(int i=0;i<=a.length;i++) m[i][0] =0;
		for(int i=0;i<=b.length;i++) m[0][1] =0;

		
		// STEP ONE : FILL THE MATRIX
		for(int i=1;i<=a.length;i++) {
			for(int j=1;j<=b.length;j++) {
				m[i][j] = (a[i-1]==b[j-1] ? m[i-1][j-1] + 1 : 0);
			}
		}

		// Printing
		for(int i=0;i<=a.length;i++) {
			for(int j=0;j<=b.length;j++) {
				System.out.print(m[i][j]);
			}
			System.out.println("");
		}
		
		// STEP TWO : LOOK FOR THE VALUES GREATER THAN 'largerthan'
		
		for(int i=a.length;i>largerthan;i--) {
			for(int j=b.length;j>largerthan;j--) {
				if (m[i][j] > largerthan) {
					StringBuilder s = new StringBuilder(m[i][j]);
					System.out.println("i:" + (i-1) + " - j: " + (j-1) + " value:" + m[i][j]) ;
					int k = i;
					int l = j;

					for (int z=m[i][j];z>0;z--) {
						s.append(a[k-1]);
						m[k--][l--] =0;
					}
					result.add(s.reverse().toString());
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] arg) {
		Primer p1 = new Primer("ACACACACACACACACACACA", "primer1", 1, 10, true);
		Primer p2 = new Primer("TGTGTGTGT", "primer2", 1, 10, true);
		
		System.out.println(p1.searchNonGappedComplementation(p2, 4));
	}
	
	// GETTERS & SETTERS
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public boolean isDirectStrand() {
		return directStrand;
	}
	public void setDirectStrand(boolean cadenaDirecta) {
		this.directStrand = cadenaDirecta;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}

