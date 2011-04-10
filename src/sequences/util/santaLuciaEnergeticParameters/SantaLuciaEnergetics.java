package sequences.util.santaLuciaEnergeticParameters;

public class SantaLuciaEnergetics {
	
	
	
	// PUBLIC INSTANCE METHODS
	/**
	 * Calculates DeltaG, DeltaS and DeltaH, values for the stability of duplex formation for a duplex
	 * of a given dna sequence. 
	 * 
	 * @param sequence
	 * @return 
	 */
	public EnergeticValues getDuplexStability(String sequence, double kelvinTemp) {
		// Assumes that sequence is a valid DNA non-degenerated sequence written in uppercase.
		EnergeticValues total = new EnergeticValues();
		EnergeticValues current = new EnergeticValues();
		
		for (int i=1; i<sequence.length(); i++) {
			current = this.getNearestNeighbor(sequence.substring(i-1, i));
			total.add(current);
		}
		
		return total;
	}
	
	/**
	 * Calculates DeltaG, DeltaS and DeltaH, values for the stability of duplex formation for a duplex
	 * of a given dna sequence. 
	 * 
	 * @param sequence
	 * @return 
	 */
	public EnergeticValues getDuplexStabilityWithEndPenalties(String sequence, double kelvinTemp) {
		// Assumes that sequence is a valid DNA non-degenerated sequence written in uppercase.
		EnergeticValues ev = this.getDuplexStability(sequence,kelvinTemp); 
		ev.add(this.get5EndPenalty(sequence.charAt(0)));
		ev.add(this.get3EndPenalty(sequence.charAt(sequence.length()-1)));
		return ev;
	}
	
	
	
	/**
	 * Retrieves DeltaG, DeltaS and DeltaH, values for the penalty stability of ending for duplex formation.
	 * @param base
	 * @param kelvinTemp
	 * @return
	 */
	public EnergeticValues getEndPenalty(char base) {
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Assumes that end5 & end3 are uppercase
				
		EnergeticValues ev = new EnergeticValues();
		
		switch(base) {
				case 'A': case 'T': ev.setDeltaH(+ 2.3);ev.setDeltaS( + 4.1);break;
				case 'C': case 'G': ev.setDeltaH(+0.1);ev.setDeltaS(-2.8);break;
		}
		
		return ev;
	
	}

	/**
	 * Retrieves DeltaG, DeltaS and DeltaH, values for the penalty stability of ending for duplex formation.
	 * @param base
	 * @param kelvinTemp
	 * @return
	 */
	public EnergeticValues get5EndPenalty(char base) {
		return getEndPenalty(base);
	
	}
	
	/**
	 * Retrieves DeltaG, DeltaS and DeltaH, values for the pena

lty stability of ending for duplex formation.
	 * @param base
	 * @param kelvinTemp
	 * @return
	 */
	public EnergeticValues get3EndPenalty(char base) {
		return getEndPenalty(base);
	}
	
	
	/**
	 * Gets DeltaG, DeltaH and DeltaS values for a single dinucleotide. 
	 * @param dinucleotide
	 * @return
	 */
	public EnergeticValues getNearestNeighbor(String dinucleotide) {
		// Assumes that dinucleotide is a valid non-degenerated DNA sequence of length of two bases amd is written uppercase.
		EnergeticValues ev = new EnergeticValues();
		
		if (dinucleotide.equals("AA")) {ev.setDeltaH(-7.9);ev.setDeltaS(-22.2);return ev;}
		if (dinucleotide.equals("AT")) {ev.setDeltaH(-7.2);ev.setDeltaS(-20.4);return ev;}
		if (dinucleotide.equals("AC")) {ev.setDeltaH(-8.4);ev.setDeltaS(-22.4);return ev;}
		if (dinucleotide.equals("AG")) {ev.setDeltaH(-7.8);ev.setDeltaS(-21.0);return ev;}
		if (dinucleotide.equals("CA")) {ev.setDeltaH(-8.5);ev.setDeltaS(-22.7);return ev;}
		if (dinucleotide.equals("CC")) {ev.setDeltaH(-8.0);ev.setDeltaS(-19.9);return ev;}
		if (dinucleotide.equals("CT")) {ev.setDeltaH(-7.8);ev.setDeltaS(-21.0);return ev;}
		if (dinucleotide.equals("CG")) {ev.setDeltaH(-10.6);ev.setDeltaS(-27.2);return ev;}
		if (dinucleotide.equals("TA")) {ev.setDeltaH(-7.2);ev.setDeltaS(-21.3);return ev;}
		if (dinucleotide.equals("TC")) {ev.setDeltaH(-8.2);ev.setDeltaS(-22.2);return ev;}
		if (dinucleotide.equals("TT")) {ev.setDeltaH(-7.9);ev.setDeltaS(-22.2);return ev;}
		if (dinucleotide.equals("TG")) {ev.setDeltaH(-8.5);ev.setDeltaS(-22.7);return ev;}
		if (dinucleotide.equals("GA")) {ev.setDeltaH(-8.2);ev.setDeltaS(-22.2);return ev;}
		if (dinucleotide.equals("GC")) {ev.setDeltaH(-9.8);ev.setDeltaS(-24.4);return ev;}
		if (dinucleotide.equals("GT")) {ev.setDeltaH(-8.4);ev.setDeltaS(-22.4);return ev;}
		if (dinucleotide.equals("GG")) {ev.setDeltaH(-8.0);ev.setDeltaS(-19.9);return ev;}
		
		return null;
		// TODO raise exception if code reachs 'return null' line
	}
	
	public EnergeticValues SaltCorrection(double monovalent, double divalent, EnergeticValues originalEnergetics, int sequenceLength) {
		// monovalent is the molar concentration of Na+ (or other monovalente  ions)
		// divalent
		
		// TODO check energy formulae from paper... 
		// TODO add formula for deltaG... 
		// TODO what happens with DeltaH?  Answer: DeltaH is usually considered salt-independent.
		
		EnergeticValues result = new EnergeticValues();

		result.setDeltaH(originalEnergetics.getDeltaH());
		
		result.setDeltaS((double) (originalEnergetics.getDeltaS() + 0.368 * (sequenceLength-1) * Math.log(monovalent)));
		
		return result;
		
	}
	
}
