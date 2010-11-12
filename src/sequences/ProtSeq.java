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

import degeneration.GeneticCode;

/**
 * This class represents a protein Sequence.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.1
 */
public class ProtSeq extends Sequence implements Apilable {
	//CONSTRUCTOR
	/**
	 * Creates a new instance of ProtSeq from a sequence and a description that is useful 
	 * to identify the sequence.
	 */
	public ProtSeq(String sequence, String description) {
		super(sequence, description);
	}

	// INSTANCE METHODS
	/** 
	 * Creates a new DNA sequence from a protein by back translation of the protein sequence.
	 * The back translation gives usually multiple choices given the degeneration of genetic code. 
	 * So the newly created DNA sequence is degenerated.
	 * 
	 * @param myGC is the genetic code used in the back translation process.
	 */
	public DNASeq backTranslate(GeneticCode myGC){

		StringBuilder result = new StringBuilder(3*this.getLength());
		
		for (int x=0;x<this.getLength();x++){
			result.append(myGC.getRetroCodon(this.getSequence().substring(x, x+1)));
		}

		return new DNASeq(result.toString(), "Back Translation of" + this.getDescription()) ;
	}

	/**
	 * this method is used to pile up a Protein sequence with another sequence into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	@Override
	public DNASeq pileUpWith(Apilable otroApilable, GeneticCode myGC) {
		return otroApilable.pileUpWithProtseq(this,myGC);
	}
	/**
	 * this method is used to pile up a Protein sequence with a DNA sequence into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	public DNASeq pileUpWithDNAseq(DNASeq otraSeq, GeneticCode myGC) {
		return otraSeq.pileUpWithProtseq(this,myGC);

	}
	/**
	 * this method is used to pile up two Protein sequences into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	public DNASeq pileUpWithProtseq(ProtSeq otraSeq, GeneticCode myGC) {
		return otraSeq.backTranslate(myGC).pileUpWithProtseq(this, myGC);
	}
}

