package degeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * This class represents a Genetic Code.
 * It is a table linking amino acids to nucleotide triplets
 */
public class GeneticCode {

	// Private Instance Variables
	private Map<String,List<String>> aminoToCodonList;
	private Map<String,String> aminoToDegCodon;
	private Map<String,String> codonToAmino;
	// Constructor
	/**
	 * Creates a new instance of genetic Code.
	 * From a path file where a genetic code is stored.
	 * @param pathfile is the path to the file containing the genetic code.
	 */
	public GeneticCode(String pathfile) throws IOException {
		this.setAminoToCodonList(new HashMap<String,List<String>>(40));
		this.setAminoToDegCodon(new HashMap<String,String>(40));
		this.setCodonToAmino(new HashMap<String,String>(100));
		this.readTableFromFile(pathfile);
		this.process();
	}
	/**
	 * Creates a new instance of genetic Code.
	 *
	 * @param pathfile is the path to the file containing the genetic code.
	 */
	public GeneticCode() {
		this.setAminoToCodonList(new HashMap<String,List<String>>(40));
		this.setAminoToDegCodon(new HashMap<String,String>(40));
		this.setCodonToAmino(new HashMap<String,String>(100));
	}

	/**
	 * This method is used to construct step by step a genetic code.
	 * In each step an amino acid must be provided, and a list of codons that
	 * codifies for it.
	 *
	 * After all amino acids were added, the method <code>process</code> must
	 * be called in order to re-arrange data in a more efficient way.
	 *
	 * @param amino is a string representing a single amino acid.
	 * @param codons is a list of triplets of nucleic acid that code for the
	 * given amino acid <code>amino</code>.
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
	 * This method returns a string representing an codon from a string
	 * representing a amino acid.
	 * An Amino acid can be coded by more than one codon. In this case all
	 * possible codons are pile up into one codon and this is returned.
	 *
	 * @param amino One of the 20 standard amino acid represented in one letter
	 * uppercase form.
	 * @return String a triplet representing a codon.
	 */
	public String getRetroCodon(String amino) {
		return this.getAminoToDegCodon().get(amino);
	}

	/**
	 * Returns an array with all the codons that codifies for a particular amino
	 * acid.
	 *
	 * @param amino One of the 20 standard amino acid represented in one letter
	 * uppercase form.
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
	 * @return String One of the 20 standard amino acid represented in one
	 * letter uppercase form.
	 */
	public String translate(String codon) {
		return this.getCodonToAmino().get(codon);
	}

	/**
	 * Piles Up two DNA sequences and returns it.
	 * <dl><dt>Preconditions:</dt>
	 * <dd>DNAseq1 y DNAseq2 Have the same length.</dd>
	 * <dd>DNAseq1 y DNAseq2 are upper case strings.</dd>
	 * <dd>DNAseq1 y DNAseq2 are formed by IUPAC degenerated code.</dd></dl>
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
	 * Piles Up two DNA sequences and returns it.
	* Ignores terminal gaps of sequences.
	* @param DNAseq1 String input DNAseq
	* @param DNAseq2 String input DNAseq
	* @return String representing the sequence of piling up the two sequences.
	*/
	public String pileUpIgnoreTerminalGaps(String DNAseq1, String DNAseq2) {
		StringBuilder sb = new StringBuilder(DNAseq1.length());

		Boolean[] gapsS1 = terminalGaps(DNAseq1);
		Boolean[] gapsS2 = terminalGaps(DNAseq2);
		for(int x=0;x<DNAseq1.length();x++) {
		if (!gapsS1[x] && !gapsS2[x]) {
			sb.append(BaseDeg.pileUpBase(DNAseq1.charAt(x), DNAseq2.charAt(x)));
		} else
		if (gapsS1[x] && gapsS2[x]) {
			sb.append("-");
		} else
		if (gapsS1[x]) {
			sb.append(DNAseq2.charAt(x));
		} else
		sb.append(DNAseq1.charAt(x));
		}
		return sb.toString();
	}
	private Boolean[] terminalGaps(String dnaseq) {
		List<Boolean> gapped = dnaseq
			.chars()
			.mapToObj(x -> Boolean.FALSE)
			.collect(Collectors.toList());
		for (int i=0; i<dnaseq.length(); i++) {
		if (dnaseq.charAt(i) == '-') {
			gapped.set(i, Boolean.TRUE);
		} else {
			break;
		}
		}

		for (int i=dnaseq.length()-1; i>0; i--) {
		if (dnaseq.charAt(i) == '-') {
			gapped.set(i, Boolean.TRUE);
		} else {
			break;
		}
		}
		return gapped.toArray(new Boolean[gapped.size()]);
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

	/**
	 * Reads a genetic code from a file.
	 *
	 * The file must have the next format:
	 * One line is used by amino acid and one more is used for stop signal
	 * (represented by '*').
	 * Each line starts with the amino acid written in one letter code. Followed
	 * by the codons that can codifies the amino acid.
	 * Each element is separated bye a comma ','.
	 *
	 * <blockquote>
	 * Example for a line:<br>
	 * "A, ATC, GGT, TTT, AAA, CAC"<br>
	 * </blockquote>
	 *
	 * @param pathfile String with the path to the file containing the genetic
	 * code.
	 */
	private void readTableFromFile(String pathfile) throws IOException {
			File f = new File(pathfile);
			BufferedReader reader = null;
			String currentLine;
			reader = new BufferedReader(new FileReader(f));
			while((currentLine = reader.readLine()) != null) {
				String[] fields = currentLine.split(",");
				if (fields.length <= 1) {
					reader.close();
					throw new IOException(
						"Genetic Code file has a format error."
					);
				}
				String amino = fields[0].trim();
				List<String>codons = IntStream
					.range(1, fields.length)
					.mapToObj(i -> fields[i].trim())
					.collect(Collectors.toList());
				codons.forEach(c -> this.getCodonToAmino().put(c, amino));
				this.getAminoToCodonList().put(amino,codons);
			}
			reader.close();
	}

	// GETTERS & SETTERS
	protected Map<String, List<String>> getAminoToCodonList() {
		return aminoToCodonList;
	}
	protected void setAminoToCodonList(
			Map<String, List<String>> aminoToCodonList) {
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
