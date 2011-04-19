package sequences.util.tmcalculator;

import degeneration.BaseDeg;
import sequences.dna.DegeneratedPrimerIterator;
import sequences.dna.Primer;

public class SantaluciaTmEstimator implements TmEstimator {
	private float min;
	private float max;
	private float mean;
	private float dh;
	private float ds;
	private float dsc;
	
	
	
	@Override
	public void calculateTM(Primer primer) {
		if(BaseDeg.getDegValueFromString(primer.getSequence())<=1) { 
			// Case Non-Degenerated
			this.mean = this.calculate(primer.getSequence()); 
			this.max = this.mean; 
			this.min = this.mean;
		} else {
			// Case Degenerated			
			DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(primer.getSequence(), 0 );
			// TODO implement a dymanic programming strategy for degenerated primers
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
	
	private float calculate(String seq) {
		this.dh=0;
		this.ds=0;
		this.dsc=0;
		
		for (int i=1; i<seq.length(); i++) {
			// Adds the Delta_H & Delta_S values according to each pair of neighbor bases. 
			this.addToDH_DS(seq.substring(i-1, i+1));
		}
		

		this.addEndsToDH_DS(seq.charAt(0), seq.charAt(seq.length()-1));
			// Adds penalties for starting and ending positions
		
		
		this.saltCorrectedDS(0.050,seq.length());
		    // Makes Salt Corrections
		
		return (float) (this.dh                                      /* delta_H in kcal/mol */ * 
				        1000                                      /* to convert kcal to cal */ /
				       (this.dsc                                          /* delta_S in cal */ + 
				       (1.987                                                   /*cal/K·mol */ ) * 
				       Math.log((double)50/1000000000L)/* Denominator is CT. assumes [50nM] */ )
				       /* For non-selfcomplementary molecules, CT in Eq. 3 is replaced by CT/4 if the
                          strands are in equal concentration or by (CA - CB/2) if the strands are at 
                          different concentrations, where CA and CB are the concentrations of the more 
                          concentrated and less concentrated strands, respectively */                   
				       - 273.15);                 /* Kelvin to Celcius */
	}
	
	private void addToDH_DS(String bases) {
		// assumes that the lenght of bases is 2 and that bases is uppercase
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Is not the most efficient way, but is easy-to-read.
		if (bases.equals("AA")) {this.dh+=-7.9;this.ds+=-22.2;return;}
		if (bases.equals("AT")) {this.dh+=-7.2;this.ds+=-20.4;return;}
		if (bases.equals("TA")) {this.dh+=-7.2;this.ds+=-21.3;return;}
		if (bases.equals("CA")) {this.dh+=-8.5;this.ds+=-22.7;return;}
		if (bases.equals("GT")) {this.dh+=-8.4;this.ds+=-22.4;return;}
		if (bases.equals("CT")) {this.dh+=-7.8;this.ds+=-21.0;return;}
		if (bases.equals("GA")) {this.dh+=-8.2;this.ds+=-22.2;return;}
		if (bases.equals("CG")) {this.dh+=-10.6;this.ds+=-27.2;return;}
		if (bases.equals("GC")) {this.dh+=-9.8;this.ds+=-24.4;return;}
		if (bases.equals("GG")) {this.dh+=-8.0;this.ds+=-19.9;return;}
		if (bases.equals("TT")) {this.dh+=-7.9;this.ds+=-22.2;return;}
		if (bases.equals("TG")) {this.dh+=-8.5;this.ds+=-22.7;return;}
		if (bases.equals("AC")) {this.dh+=-8.4;this.ds+=-22.4;return;}
		if (bases.equals("AG")) {this.dh+=-7.8;this.ds+=-21.0;return;}
		if (bases.equals("TC")) {this.dh+=-8.2;this.ds+=-22.2;return;}
		if (bases.equals("CC")) {this.dh+=-8.0;this.ds+=-19.9;return;}
	}
	
	private void addEndsToDH_DS(char end5,char end3) {
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Assumes that end5 & end3 are uppercase
		char[] ends = new char[]{end5,end3};

		for (char c : ends) {
			switch(c) {
				case 'A': case 'T': this.dh+=2.3;this.ds+=4.1;break;
				case 'C': case 'G': this.dh+=0.1;this.ds+=-2.8;break;
			}
		}
	}

	private void saltCorrectedDS(double M_Na, int len) {
		// M_Na is the molar concentration of Na+ (or other monovalente  ions)
		this.dsc =  (float) (this.ds + 0.368 * (len-1) * Math.log(M_Na));
	}

	
}
