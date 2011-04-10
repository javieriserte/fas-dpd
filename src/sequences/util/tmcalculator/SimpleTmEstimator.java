package sequences.util.tmcalculator;

import degeneration.BaseDeg;
import sequences.dna.DegeneratedPrimerIterator;
import sequences.dna.Primer;
/**
 * 
 * Simple Tm Calculator:
 * Tm (°C) = 2 * AT + 4 * GC
 * @author jiserte
 *
 */
public class SimpleTmEstimator implements TmEstimator {

	private float min=0;
	private float max=0;
	private float mean=0;
	
	// PUBLIC INSTANCE METHODS

	@Override public void calculateTM(Primer primer) {

		if(BaseDeg.getDegValueFromString(primer.getSequence())<=1) { 
			// Case Non-Degenerated
			this.mean = this.calculate(primer.getSequence()); 
			this.max = this.mean; 
			this.min = this.mean;
		} else {
			
			DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(primer.getSequence(), 0 );
			float total = 0;
			int n=0;
			dpi.start();
			String s = dpi.next();
			float tm = this.calculate(s);
			this.max = tm;
			this.min = tm;
			total +=tm;
			n++;
			
			while (dpi.hasNext()) {
				tm = this.calculate(dpi.next());
				this.max = Math.max(this.max, tm);
				this.min = Math.min(this.min, tm);
				total +=tm;
				n++;
			}
			this.mean = (float) (total / n);
		
		}
		
	}

	@Override public float mean() {
		return this.mean;
	}

	@Override public float max() {
		return this.max;
	}

	@Override public float min() {
		return this.min;
	}
	

	// PRIVATE INSTANCE METHODS
	
	private int count(String sequence, char[] searched) {
		// Assumes that sequence is UpperCase
		int result=0;
		for (int i=0;i<sequence.length();i++) {
			char c = sequence.charAt(i) ;
			
			for (char ci : searched) {
				if (ci==c) { result = result + 1; break; }
			}
		}
		return result;
	}

	private int countAT(String sequence) {
		// Assumes that sequence is UpperCase
		return this.count(sequence, new char[]{'A','T'});
	}
	
	private int countCG(String sequence) {
		// Assumes that sequence is UpperCase		
		return this.count(sequence, new char[]{'C','G'});

	}
	
	private float calculate(String Seq) {
		int at;
		int cg;
		at = this.countAT(Seq);
		cg = this.countCG(Seq);
		return  2 * at + 4 * cg;
	}
}
