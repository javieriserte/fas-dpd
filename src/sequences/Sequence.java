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
package sequences;
/**
 * This abstract class represents a sequence. 
 * Stores the sequence itself and a description useful to identify it.
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.6
 */
public abstract class Sequence implements Apilable {
	
	// INSTANCE VARIABLES
	private String sequence;
	private String description;

	// CONSTRUCTOR
	/**
	 * Creates a new sequence.
	 */
	public Sequence(String sequence, String description) {
		super();
		this.setSequence(sequence);
		this.description = description;
	}

	// INSTANCE METHODS
	/**
	 * return the length of the sequence.
	 */
	public int getLength() {
		return this.getSequence().length();
	}
	
	// GETTERS & SETTERS
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence.toUpperCase();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
