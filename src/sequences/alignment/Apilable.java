package sequences.alignment;

import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
/**
 * Interface to represent the operation of piling up DNA and protein sequences.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public interface Apilable {
	public DNASeq pileUpWith(Apilable anotherApilable, GeneticCode myGC);
	public DNASeq pileUpWithDNAseq(DNASeq anotherSeq, GeneticCode myGC);
	public DNASeq pileUpWithProtseq(ProtSeq anotherSeq, GeneticCode myGC);
}
