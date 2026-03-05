package sequences.util.compare;

import sequences.dna.DNASeq;

public class NonDegeneratedDNAMatchingStrategy implements MatchingStrategy {

	@Override
	public double matches(char a, char b) {
		if (b == DNASeq.getComplementaryBase(a)) return 1;
		return 0;
	}

}
