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
package sequences.alignment;

import java.util.List;
import java.util.Vector;

import sequences.Sequence;
import sequences.dna.DNASeq;

import degeneration.GeneticCode;

/**
 * This class represents an alignment of sequences.
 * A invariant of representation is that all the sequences had the same length.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.1
 */
public class Alignment {
	
	// INSTANCE VARIABLES
	protected List<Sequence> seq;

	// CONSTRUCTOR
	/**
	 * Creates a new alignment from a list of sequences.
	 */
	public Alignment(List<Sequence> seq) {
		super();
		this.setSeq(seq);
	}
	/**
	 * Creates an empty alignment
	 */
	public Alignment(){
		super();
		this.setSeq( new Vector<Sequence>());
	}

	// INSTANCE METHOD
	public void addSequence(Sequence seq) {
		this.getSeq().add(seq);
	}
	
	/**
	 * Removes a sequence from the alignment.
	 * Assumes that there is not two or more sequences with the same description. 
	 * @param Description is a String to identify a sequence.
	 */
	public void removeSequence(String Description) {
		List<Sequence> l = new Vector<Sequence>();
		l.addAll(this.getSeq());
		for (Sequence seq : this.getSeq()) {
			if (seq.getDescription().equals(Description)) {
				l.remove(seq);
			}
		}
		this.setSeq(l);
	}
	
	/** 
	 * Checks that all sequences have a different description.
	 * 
	 * @return true. If all of them are different, false otherwise.
	 */
	public boolean verifyDifferentDescriptions(){
	
		for(int x=0;x<this.getSeq().size()-1;x++) {
			for(int y=x+1;y<this.getSeq().size();y++) {
				if (this.getSeq().get(x).getDescription()==this.getSeq().get(y).getDescription()) {return false;} 
			}
		}
		return true;
	}
	
	/**
	 * This method is used to create a single DNA sequence that is the consensus of all the sequences in the alignment.
	 * When the alignment is of Proteins, each sequence is back translated to DNA.
	 * 
	 * @param myGC a genetic code. Used when proteins must be back translated to DNA.
	 * @return a DNASeq representing the consensus of the alignment.
	 */
	public DNASeq pileUp(GeneticCode myGC){

		Sequence result =  this.getSeq().get(0);
		
		for (Sequence sec : this.getSeq()) {
			
			result = result.pileUpWith(sec, myGC);
			
		}
		return (DNASeq) result;
	}

	/**
	 * @return int the length of all sequences in the alignment.
	 */
	public int lenght() {
		return this.getSeq().get(0).getLength();
			// all the sequences must have the same length
	}

	// Getters & Setter
	public List<Sequence> getSeq() {
		return seq;
	}
	public void setSeq(List<Sequence> seq) {
		this.seq = seq;
	}
	
	
}
