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

package sequences.util.gccontent;

import degeneration.BaseDeg;

public class GCContent {

	/**
	 * Calculates the G+C content of a DNA sequence.
	 * 
	 * @param sequence. Is a DNA sequence in uppercase. Can be a degenerated sequence.
	 * @return
	 */
	static public float calculateGCContent(String sequence) {
		float[] TotalBases = new float[4];
		int[] Currentbases = new int[4];
			// each position in bases array is used to count the number of each base in the primer.
		    // First position is A, second is C, third is T, fourth is G.
		
		for (int l=0; l<sequence.length();l++) {
			// Loop for every base in the primer
			for( int i=0;i<4;i++) {
					// loop for each posible non-degenerated base.
				boolean c = BaseDeg.containsBaseIntInChar(i, sequence.charAt(l));
					// looks the current base in the primer contains each posible base.
				if(c) Currentbases[i]=1; else Currentbases[i]=0; 
					// if the base is present in a given position of a primer, puts one in Currentbases array.
			}
			int total = 0; for (int i=0;i<4;i++) total+=Currentbases[i];
				// get the degeneration value for a given position.
				
			for (int i=0;i<4;i++) {
				TotalBases[i]+=Currentbases[i]/total;
					// TotalBases stores the cummulated frequencie for each non-degenerated base.
			}
		}	
		float totalACTG = TotalBases[0]+ TotalBases[1]+ TotalBases[2]+ TotalBases[3];

		float totalCG = TotalBases[1]+ TotalBases[3];
		
		return totalCG/totalACTG;
	}

	
}
