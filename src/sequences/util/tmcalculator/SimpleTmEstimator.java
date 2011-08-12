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

	private double  min=0;
	private double  max=0;
	private double  mean=0;
	
	// PUBLIC INSTANCE METHODS

	@Override public void calculateTM(Primer primer) {

		if(BaseDeg.getDegValueFromString(primer.getSequence())<=1) { 
			// Case Non-Degenerated
			this.mean = this.calculate(primer.getSequence()); 
			this.max = this.mean; 
			this.min = this.mean;
		} else {
			double gc = 0;
			double at =0;
			double min =0;
			double max = 0;
			for (int i=0;i<primer.getSequence().length();i++) {
				char c = primer.getSequence().charAt(i);
				DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(String.valueOf(c));
				dpi.start();
				StringBuilder posibilities = new StringBuilder();
				
				while (dpi.hasNext()) {
					posibilities.append(dpi.next());
				}
				
				int countCG = this.countCG(posibilities.toString());
				int length = posibilities.length();
				gc +=  (double) countCG / (double) length;
				at += 1 - gc;
				
				if (countCG>0) max+=4 ;else max+=2;
				if (length - countCG >0) min+=2; else min+=4;
				
			}
			
			this.mean = gc*4 + at*2;
			this.max = max;
			this.min = min;
		
		}
		
	}

	@Override public double  mean() {
		return this.mean;
	}

	@Override public double  max() {
		return this.max;
	}

	@Override public double  min() {
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
	
	private double calculate(String Seq) {
		int at;
		int cg;
		at = this.countAT(Seq);
		cg = this.countCG(Seq);
		return  2 * at + 4 * cg;
	}
}
