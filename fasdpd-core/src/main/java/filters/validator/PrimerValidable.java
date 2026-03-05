package filters.validator;

import sequences.dna.Primer;

public class PrimerValidable implements Validable {
	private Primer primer;
	
	public PrimerValidable(Primer primer) {
		this.primer = primer;
	}
	
	
	// GETTER
	
	public Primer getPrimer() {
		return this.primer;
	}
	
	
}
