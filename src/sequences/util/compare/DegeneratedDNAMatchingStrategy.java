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

package sequences.util.compare;

import sequences.dna.DNASeq;
import degeneration.BaseDeg;

public class DegeneratedDNAMatchingStrategy implements MatchingStrategy {

	static private double[][] matchingValues = DegeneratedDNAMatchingStrategy.getMatchingMatrix();

	
	@Override
	public double matches(char a, char b) {
		int p1 = BaseDeg.getIntFromChar(a);
		int p2 = BaseDeg.getIntFromChar(b);
		
		
		return DegeneratedDNAMatchingStrategy.matchingValues[p1][p2];
		
		
		
	}
	
	public static void printMatchingMatrix() {
	
		StringBuilder line= new StringBuilder();
		
		line.append("\t");
		for (int i=1;i<16;i++) line.append(BaseDeg.getCharFromInt(i)+"\t");
		
		line.append("\r\n");
		
		
		for (int i=1;i<16;i++) {
			// start in 1, because 0 is for '-'
				line.append(BaseDeg.getCharFromInt(i));
			for (int j=1;j<16;j++) {
				line.append("\t"+matchingValues[i][j]);
			}
			line.append("\r\n");
		}
		
		System.out.println(line);
		
	}
	
	
	static private double[][] getMatchingMatrix() {
		double values[][] = new double[16][16];
		for (int i=1;i<16;i++) {
			// start in 1, because 0 is for '-' 
			for (int j=i;j<16;j++) {
				
				char[] b1 = BaseDeg.getCharArrayFromChar(DNASeq.getComplementaryBase(BaseDeg.getCharFromInt(i)));
				char[] b2 = BaseDeg.getCharArrayFromInt(j);
				
				double cases= 0;
				for (char c1 : b1) {
					for (char c2 : b2) {
						if (c1==c2) cases++;
					}
				}
				values[i][j] = cases / (b1.length*b2.length);
				values[j][i] = values[i][j];
			}
		}
		return values;
	}

}
