package sequences.dna;

import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import sequences.util.compare.SequenceComparator;

/**
 * Class to represent a Oligonucleotide that is Primer for PCR.
 * Extend of DNAseq.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public class Primer extends DNASeq implements Comparable<Primer>{
	private float score;
	private int start;
	private int end;
	private boolean directStrand;

	/**
	 * Creates a new primer from a sequence and a description.
	 * Also requires a start, end and directStrand values that
	 * references to the sequence from where the primer was designed.
	 *
	 */
	public Primer(String sequence, String description,int start,int end, boolean directStrand) {
		super(sequence,description);
		this.setStart(start);
		this.setEnd(end);
		this.setDirectStrand(directStrand);
	}

	/**
	 * Implementation of comparable interface.
	 * Primers are comparable by a score.
	 */
	@Override
	public int compareTo(Primer anotherPrimer) {
		return ((Float) this.getScore()).compareTo(anotherPrimer.getScore());
	}

	public static void main(String[] arg) {
		Primer p1 = new Primer("CACAAAAAAAAAAAAAAA", "primer1", 1, 10, true);
		Primer p2 = new Primer("TTGTGCCCCCCCCCCCCC", "primer2", 1, 10, true);

		System.out.println(SequenceComparator.getListOfNonGappedComplementaryRegions(p1.getSequence(),p2.getSequence(), 4, new DegeneratedDNAMatchingStrategy()));
		
		p1 = new Primer("AAAAAAAAAAAAACACAA", "primer1", 1, 10, true);
		p2 = new Primer("TTGTGCCCCCCCCCCCCC", "primer2", 1, 10, true);

		System.out.println(SequenceComparator.getListOfNonGappedComplementaryRegions(p1.getSequence(),p2.getSequence(), 4, new DegeneratedDNAMatchingStrategy()));
		
		p1 = new Primer("AAAAAAAAAAAAACACAA", "primer1", 1, 10, true);
		p2 = new Primer("CCCCCCCCCCCCCTTGTG", "primer2", 1, 10, true);

		System.out.println(SequenceComparator.getListOfNonGappedComplementaryRegions(p1.getSequence(),p2.getSequence(), 4, new DegeneratedDNAMatchingStrategy()));
		
		p1 = new Primer("CACAAAAAAAAAAAAAAA", "primer1", 1, 10, true);
		p2 = new Primer("CCCCCCCCCCCCCTTGTG", "primer2", 1, 10, true);

		System.out.println(SequenceComparator.getListOfNonGappedComplementaryRegions(p1.getSequence(),p2.getSequence(), 4, new DegeneratedDNAMatchingStrategy()));

		p1 = new Primer("AACACAAAAAAAAAAAAA", "primer1", 1, 10, true);
		p2 = new Primer("CCCCCCTTGTGCCCCCCC", "primer2", 1, 10, true);

		System.out.println(SequenceComparator.getListOfNonGappedComplementaryRegions(p1.getSequence(),p2.getSequence(), 4, new DegeneratedDNAMatchingStrategy()));


	}
	// GETTERS & SETTERS
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public boolean isDirectStrand() {
		return directStrand;
	}
	public void setDirectStrand(boolean cadenaDirecta) {
		this.directStrand = cadenaDirecta;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}

