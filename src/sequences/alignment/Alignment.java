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

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.text.Segment;

import sequences.Sequence;
import sequences.dna.DNASeq;

import degeneration.BaseDeg;
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

	
	/**
	 * 
	 * 
	 * 
  	 *			Aspartic Acid	2,98	red
 	 *			Glutamic Acid	3,08	red
	 *			Cysteine	5,02	amarillo
	 *			Asparagine	5,41	amarillo
	 *			Threonine	5,6	amarillo
	 *			Tyrosine	5,63	amarillo
	 *			Glutamine	5,65	amarillo
	 *			Serine	5,68	amarillo
	 *			Methionine	5,74	amarillo
	 *			Tryptophan	5,88	amarillo
	 *			Phenylalanine	5,91	amarillo
	 *			Valine	6,02	verde
	 *			Isoleucine	6,04	verde
	 *			Leucine	6,04	verde
	 *			Glycine	6,06	verde
	 *			alanine	6,11	verde
	 *			Proline	6,3	verde
	 *			Histidine	7,64	verde
	 *			Lysine	9,47	blue
	 *			Arginine	10,76	blue
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public String exportHTML_highlated(Integer from, Integer to, Color col) {
		String header = "<html><tt>";
		String end = "</tt></html>";
		String endlin = "<br>";
		String template = "<font color = #1 >#L</font>";
		String templateHigh = "<font color = #1 bgcolor = #2 >#L</font>";
		StringBuilder result= new StringBuilder();
		int l = this.getSeq().size();
		
		Sequence sequence = this.getSeq().get(0);
		String bgcolor = "#"+ Integer.toHexString(col.getRed()) + 
		                      Integer.toHexString(col.getGreen()) + 
		                      Integer.toHexString(col.getBlue());
		
		if (sequence.getClass()==DNASeq.class) {
			// is DNA!!
			Map<Character,String> dnaTocol = new HashMap<Character,String>();
			
			dnaTocol.put('A', "green");
			dnaTocol.put('C', "blue");
			dnaTocol.put('T', "red");
			dnaTocol.put('G', "black");
			dnaTocol.put('-', "gray");
			
			
		result.append(header);
			for (int i=0;i<l;i++) {
			
				sequence = this.getSeq().get(i);
				for (int j=0;j<sequence.getLength();j++) {
					
					char c = sequence.getSequence().charAt(j);
					String cold = this.getHTMLcol(c, dnaTocol,"pink");
					String temp = "";

					if (j>=from&&j<=to) {
						temp = temp.replaceAll("#2", bgcolor);
					} else {
					}

					temp = template.replaceAll("#1", cold);
					temp = temp.replaceAll("#L", String.valueOf(c));
					result.append(temp);
				}
				result.append(endlin);
			}
			result.append(end);
		} else {
			// is Protein!!
			
			Map<Character,String> protTocol = new HashMap<Character,String>();
			
			protTocol.put('D', "red");
			protTocol.put('E', "red");
			protTocol.put('C', "yellow");
			protTocol.put('N', "yellow");
			protTocol.put('T', "yellow");
			protTocol.put('Y', "yellow");
			protTocol.put('Q', "yellow");
			protTocol.put('M', "yellow");
			protTocol.put('W', "yellow");
			protTocol.put('F', "yellow");
			protTocol.put('V', "green");			
			protTocol.put('I', "green");
			protTocol.put('L', "green");
			protTocol.put('G', "green");
			protTocol.put('A', "green");
			protTocol.put('P', "green");
			protTocol.put('H', "green");
			protTocol.put('K', "blue");			
			protTocol.put('R', "blue");
			protTocol.put('-', "gray");

			for (int i=0;i<l;i++) {
				
				sequence = this.getSeq().get(i);
				for (int j=0;j<sequence.getLength();j++) {
					
					char c = sequence.getSequence().charAt(j);
					String cold = this.getHTMLcol(c, protTocol,"black");
					String temp = "";

					if (j>=from&&j<=to) {
						temp = temp.replaceAll("#2", bgcolor);
					} else {
					}

					temp = template.replaceAll("#1", cold);
					temp = temp.replaceAll("#L", String.valueOf(c)+ "  ");
					result.append(temp);
				}
				result.append(endlin);
			}
			result.append(end);
			
		}
		return result.toString();
	}
	
	private String getHTMLcol(char c, Map<Character,String> colormap, String defCol) {
		
		String col = colormap.get(c);
		if (col==null) col = defCol;  
		return col;
	}
	
	
	
	// Getters & Setter
	public List<Sequence> getSeq() {
		return seq;
	}
	public void setSeq(List<Sequence> seq) {
		this.seq = seq;
	}
	
	
}
