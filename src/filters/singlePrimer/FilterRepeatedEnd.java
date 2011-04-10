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
package filters.singlePrimer;

import sequences.dna.Primer;
/**
 * This class filters primers that has the two last positions base the same base. 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.2
 */
public class FilterRepeatedEnd extends FilterSinglePrimer {
	/**
	 * Validates that a primer is repeated in the last two position.
	 * 
	 * @return false if the end is repeated
	 * 			true otherwise.
	 */
	public boolean filter(Primer p) {
		return !((p.getSequence().charAt(p.getSequence().length()-1)) == 
			    (p.getSequence().charAt(p.getSequence().length()-2)));
	}
	
	
}
