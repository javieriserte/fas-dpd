/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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
 * 	"Microbiolog�a molecular b�sica y aplicaciones biotecnol�gicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingenier�a Gen�tica y Biolog�a Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Go�i.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package sequences.protein;

import sequences.Sequence;
import sequences.alignment.Apilable;
import sequences.dna.DNASeq;
import degeneration.GeneticCode;

/**
 * This class represents a protein Sequence.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class ProtSeq extends Sequence {
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
			String currentAminoAcid = this.getSequence().substring(x, x+1);
			String retroCodon = myGC.getRetroCodon(currentAminoAcid);
			if (retroCodon==null) { // If gaps or non amino acid chars are found, an "NNN" codon is assigned.
				retroCodon = "NNN";
			}
			result.append(retroCodon);
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

	@Override
	public Sequence toDNA() {
		return new DNASeq(this.getSequence(), this.getDescription());
	}

	@Override
	public Sequence toProtein() {
		return this;
	}
}

