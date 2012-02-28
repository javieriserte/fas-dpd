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

package sequences.util.tmcalculator;

import degeneration.BaseDeg;
import sequences.dna.DegeneratedPrimerIterator;
import sequences.dna.Primer;
/**
 * 
 * Simple Tm Calculator:
 * Tm (°C) = 2 * AT + 4 * GC
 * @author jiserte
 *
 */
public class SimpleTmEstimator implements TmEstimator {

	private double  min=0;
	private double  max=0;
	private double  mean=0;
	
	// PUBLIC INSTANCE METHODS

	@Override public void calculateTM(Primer primer) {

		if(BaseDeg.getDegValueFromString(primer.getSequence())<=1) { 
			// Case Non-Degenerated
			this.mean = this.calculate(primer.getSequence()); 
			this.max = this.mean; 
			this.min = this.mean;
		} else {
			double gc = 0;
			double at = 0;
			double min =0;
			double max = 0;
			for (int i=0;i<primer.getSequence().length();i++) {
				char c = primer.getSequence().charAt(i);
				DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(String.valueOf(c));
				dpi.start();
				StringBuilder posibilities = new StringBuilder();
				
				while (dpi.hasNext()) {
					posibilities.append(dpi.next());
				}
				
				int countCG = this.countCG(posibilities.toString());
				int length = posibilities.length();
				gc +=  (double) countCG / (double) length;
				at += 1 - gc;
				
				if (countCG>0) max+=4 ;else max+=2;
				if (length - countCG >0) min+=2; else min+=4;
				
			}
			
			this.mean = gc*4 + at*2;
			this.max = max;
			this.min = min;
		
		}
		
	}

	@Override public double  mean() {
		return this.mean;
	}

	@Override public double  max() {
		return this.max;
	}

	@Override public double  min() {
		return this.min;
	}
	

	// PRIVATE INSTANCE METHODS
	
	private int count(String sequence, char[] searched) {
		// Assumes that sequence is UpperCase
		int result=0;
		for (int i=0;i<sequence.length();i++) {
			char c = sequence.charAt(i) ;
			
			for (char ci : searched) {
				if (ci==c) { result = result + 1; break; }
			}
		}
		return result;
	}

	private int countAT(String sequence) {
		// Assumes that sequence is UpperCase
		return this.count(sequence, new char[]{'A','T'});
	}
	
	private int countCG(String sequence) {
		// Assumes that sequence is UpperCase		
		return this.count(sequence, new char[]{'C','G'});

	}
	
	private double calculate(String Seq) {
		int at;
		int cg;
		at = this.countAT(Seq);
		cg = this.countCG(Seq);
		return  2 * at + 4 * cg;
	}
}
