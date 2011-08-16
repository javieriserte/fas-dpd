package degeneration;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
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
/**
 * This class represents a Genetic Code.
 * Is a table linking amino acids to nucleotide triplets
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class GeneticCode {

	private Map<String,List<String>> aminoToCodonList;
	private Map<String,String> aminoToDegCodon;
	private Map<String,String> codonToAmino;
	
	// CONSTRUCTOR
	/**
	 * Creates a new instance of genetic Code.
	 * From a path file where a genetic code is stored.
	 * 
	 */
	public GeneticCode(String pathfile) {
		this.setAminoToCodonList(new HashMap<String,List<String>>(40));
		this.setAminoToDegCodon(new HashMap<String,String>(40));
		this.setCodonToAmino(new HashMap<String,String>(100));
		this.readTableFromFile(pathfile);
		
//		for (String amino : this.getAminoToCodonList().keySet()) {
//			String codonRes = this.getAminoToCodonList().get(amino).get(0);
//			for (String codon : this.getAminoToCodonList().get(amino)) {
//				codonRes = this.pileUp(codonRes, codon);
//			}
//			this.getAminoToDegCodon().put(amino, codonRes);
//		}
		this.process();
	}
	
	public GeneticCode() {
		this.setAminoToCodonList(new HashMap<String,List<String>>(40));
		this.setAminoToDegCodon(new HashMap<String,String>(40));
		this.setCodonToAmino(new HashMap<String,String>(100));
	}
	
	
	
	
	//INSTANCE METHODS

	/**
	 * This method is used to construct step by step a genetic code.
	 * In each step an amino acid must be provided, and a list of codons that codifies for it.
	 * 
	 * After all amino acids were added, the method process must be called in order to re-arrange data in a more efficient way. 
	 *  
	 */
	public void addCodons(String amino,List<String> codons) {

		List<String> codonl = new Vector<String>();
		for (int i=0;i<codons.size();i++) {
			codonl.add(codons.get(i).trim());
			this.getCodonToAmino().put(codons.get(i).trim(), amino);
			}
		this.getAminoToCodonList().put(amino,codonl);
	}

	/**
	 * This method is used to construct step by step a genetic code.
	 * The first to do is call many times the method 'addCodons'.
	 * Then, after all amino acids were added, the method process must 
	 * be called in order to re-arrange data in a more efficient way.
	 * 
	 * 
	 */
	public void process() {
		for (String amino : this.getAminoToCodonList().keySet()) {
			String codonRes = this.getAminoToCodonList().get(amino).get(0);
			for (String codon : this.getAminoToCodonList().get(amino)) {
				codonRes = this.pileUp(codonRes, codon);
			}
			this.getAminoToDegCodon().put(amino, codonRes);
		}
	}
	
	
	/**
	 * This method returns a string representing an codon from a string representing a amino acid.
	 * An Amino acid can be coded by more than one codon. In this case all possible codons are pile up into one codon and this is returned.
	 * 
	 * @param amino One of the 20 standard amino acid represented in one letter uppercase form.  
	 * @return String a triplet representing a codon.
	 */
	public String getRetroCodon(String amino) {
		return this.getAminoToDegCodon().get(amino);
	}
	
	/**
	 * Returns an array with all the codons that codifies for a particular amino acid. 
	 * 
	 * @param amino One of the 20 standard amino acid represented in one letter uppercase form. 
	 * @return a list of triplets, each one representing a codon.
	 */
	public List<String> getCodonArray(String amino){
		return this.getAminoToCodonList().get(amino);
	}
	
	/**
	 * Translate a single codon into an amino acid.
	 * The codon must be formed with non degenerated bases.
	 * 
	 * @param codon String that represents a codon.
	 * @return String One of the 20 standard amino acid represented in one letter uppercase form.
	 */
	public String translate(String codon) {
		return this.getCodonToAmino().get(codon);
	}
	
	/**
	 * Piles Up two DNA sequences and returns it.
	 * 
	 * Preconditions:  DNAseq1 y DNAseq2 Have the same length
	 * 				   DNAseq1 y DNAseq2 are upper case strings.
	 *                 DNAseq1 y DNAseq2 are formed by IUPAC degenerated code.
	 * 
	 * @param DNAseq1 String input DNAseq
	 * @param DNAseq2 String input DNAseq
	 * @return String representing the sequence of piling up the two sequences.
	 */
	public String pileUp(String DNAseq1, String DNAseq2) {
		StringBuilder sb = new StringBuilder(DNAseq1.length());

		for(int x=0;x<DNAseq1.length();x++) {
			sb.append(BaseDeg.pileUpBase(DNAseq1.charAt(x), DNAseq2.charAt(x)));
		}
		return sb.toString();
	}
	
	/**
	 * Calculates the degeneration value for a particular base.
	 * @param base string a degenerated base
	 * @return int value from 0 to 4.
	 */
	public int calculateDegValue(String base){
		return calculateDegValue(base.charAt(0));
	}
	/**
	 * Calculates the degeneration value for a particular base.
	 * @param base string a degenerated base
	 * @return int value from 0 to 4.
	 */
	public int calculateDegValue(char base){
		return BaseDeg.getDegValueFromChar(base);
		
	}

	
	// MÉTODOS PRIVADOS
	/**
	 * Reads a genetic code from a file.
	 * 
	 * The file must have the next format:
	 * One line is used by amino acid and one more is used for stop signal (represented by '*').
	 * Each line starts with the amino acid written in one letter code. Followed by the codons that can codifies the amino acid.
	 * Each element is separated bye a comma ','.
	 * 
	 * <blockquote>
	 * Example for a line:<br>		
	 * "A, ATC, GGT, TTT, AAA, CAC"<br>
	 * </blockquote> 
	 * 
	 * @param pathfile String with the path to the file containing the genetic code.   
	 */
	private void readTableFromFile(String pathfile) {
			File f = new File(pathfile);
			BufferedReader br = null;
			String d="";
			String[] s;
			
			boolean exit = false;
			try {
				br = new BufferedReader(new FileReader(f));
				
				while(!exit) {
					d = br.readLine();
					if (d==null) {exit=true;}
					else {
						s = d.split(",");
						String amino = s[0].trim();
						List<String>codons = new Vector<String>();
						for (int i=1;i<s.length;i++) {
							codons.add(s[i].trim());
							this.getCodonToAmino().put(s[i].trim(), amino);
							}
						this.getAminoToCodonList().put(amino,codons);
						
					}					
				}
			} catch (EOFException e) {
				// EOF - OK.
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	

	// GETTERS & SETTERS
	protected Map<String, List<String>> getAminoToCodonList() {
		return aminoToCodonList;
	}
	protected void setAminoToCodonList(Map<String, List<String>> aminoToCodonList) {
		this.aminoToCodonList = aminoToCodonList;
	}
	protected Map<String, String> getAminoToDegCodon() {
		return aminoToDegCodon;
	}
	protected void setAminoToDegCodon(Map<String, String> aminoToDegCodon) {
		this.aminoToDegCodon = aminoToDegCodon;
	}
	protected Map<String, String> getCodonToAmino() {
		return codonToAmino;
	}
	protected void setCodonToAmino(Map<String, String> codonToAmino) {
		this.codonToAmino = codonToAmino;
	}
}
