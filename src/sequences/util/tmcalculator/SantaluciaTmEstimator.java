/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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
 * 	"Microbiolog�a molecular b�sica y aplicaciones biotecnol�gicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingenier�a Gen�tica y Biolog�a Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Go�i.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package sequences.util.tmcalculator;

import degeneration.BaseDeg;
import sequences.dna.DegeneratedPrimerIterator;
import sequences.dna.Primer;

public class SantaluciaTmEstimator implements TmEstimator {
	private double min;
	private double max;
	private double mean;
	private double dh;
	private double ds;
	private double dsc;
	
	
	
	@Override
	public void calculateTM(Primer primer) {
		String sequence = primer.getSequence();
		
		int degValue = BaseDeg.getDegValueFromString(sequence);
		if(degValue<=1) { 
			// Case Non-Degenerated
			this.mean = this.calculate(sequence);
			this.max = this.mean; 
			this.min = this.mean;
		} else {
			// Case Degenerated			
			this.mean = this.calculateDeg(sequence); 
			this.max = this.mean; 
			this.min = this.mean;
			
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
	
	private double calculateDeg(String seq) {
		
		// This method is approximated
		this.dh=0;
		this.ds=0;
		this.dsc=0;
		
		double[] dhds;
		
		for (int i=1; i<seq.length(); i++) {
			// Adds the Delta_H & Delta_S values according to each pair of neighbor bases. 
			
			String dinucleotide = seq.substring(i-1, i+1);

			dhds = getDH_DS_Deg(dinucleotide);
			
			this.dh += dhds[0];
			this.ds += dhds[1];
			
		}

		dhds = this.getEndPenaltiesDH_DS_DEG(seq.charAt(0));
		this.dh += dhds[0];
		this.ds += dhds[1];
		
		dhds = this.getEndPenaltiesDH_DS_DEG(seq.charAt(seq.length()-1));
		this.dh += dhds[0];
		this.ds += dhds[1];
			// Adds penalties for starting and ending positions
		
		this.dsc = saltCorrectedDS(0.050D,seq.length(),this.ds);		
		    // Makes Salt Corrections
		
		return getMeltingTemp(this.dh, this.dsc);                 
	}

	protected double getMeltingTemp(double dh, double ds) {
		return (double) (dh                                      /* delta_H in kcal/mol */ * 
				        (double)1000                                      /* to convert kcal to cal */ /
				        (ds                                          /* delta_S in cal */ + 
				        ((double)1.987                                                   /*cal/K�mol */ ) * 
				        Math.log((double)(50/1000000000D))/* Denominator is CT. assumes [50nM] */ )
				        /* For non-selfcomplementary molecules, CT in Eq. 3 is replaced by CT/4 if the
                          strands are in equal concentration or by (CA - CB/2) if the strands are at 
                          different concentrations, where CA and CB are the concentrations of the more 
                          concentrated and less concentrated strands, respectively */                   
				       - (double) 273.15); /* Kelvin to Celcius */
	}
	
	private double  calculate(String seq) {
		this.dh = 0 ;
		this.ds = 0 ;
		this.dsc= 0 ;
		
		for (int i=1; i<seq.length(); i++) {
			// Adds the Delta_H & Delta_S values according to each pair of neighbor bases. 
			String dinucleotide = seq.substring(i-1, i+1);
			double[] dhds = this.getDH_DS(dinucleotide);
			
			this.dh += dhds[0];
			this.ds += dhds[1];
			
//			this.addToDH_DS(dinucleotide);
			
		}
		
		double[] dhds = this.getEndPenalty_DH_DS(seq.charAt(0));
		this.dh += dhds[0];
		this.ds += dhds[1];
		
		dhds = this.getEndPenalty_DH_DS(seq.charAt(seq.length()-1));
		this.dh += dhds[0];
		this.ds += dhds[1];
			// Adds penalties for starting and ending positions
		
		
		this.dsc = saltCorrectedDS(0.050D,seq.length(),this.ds);
		
		    // Makes Salt Corrections
		
		return getMeltingTemp(this.dh,this.dsc);                 
	}
	
//	private void addToDH_DS(String bases) {
//		// assumes that the lenght of bases is 2 and that bases is uppercase
//		// DH values are in Kcal/mol, while DS values are in cal/mol
//		// Is not the most efficient way, but is easy-to-read.
//		if (bases.equals("AA")) {this.dh+=-7.9 ;this.ds+=-22.2;return;}
//		if (bases.equals("AT")) {this.dh+=-7.2 ;this.ds+=-20.4;return;}
//		if (bases.equals("TA")) {this.dh+=-7.2 ;this.ds+=-21.3;return;}
//		if (bases.equals("CA")) {this.dh+=-8.5 ;this.ds+=-22.7;return;}
//		if (bases.equals("GT")) {this.dh+=-8.4 ;this.ds+=-22.4;return;}
//		if (bases.equals("CT")) {this.dh+=-7.8 ;this.ds+=-21.0;return;}
//		if (bases.equals("GA")) {this.dh+=-8.2 ;this.ds+=-22.2;return;}
//		if (bases.equals("CG")) {this.dh+=-10.6;this.ds+=-27.2;return;}
//		if (bases.equals("GC")) {this.dh+=-9.8 ;this.ds+=-24.4;return;}
//		if (bases.equals("GG")) {this.dh+=-8.0 ;this.ds+=-19.9;return;}
//		if (bases.equals("TT")) {this.dh+=-7.9 ;this.ds+=-22.2;return;}
//		if (bases.equals("TG")) {this.dh+=-8.5 ;this.ds+=-22.7;return;}
//		if (bases.equals("AC")) {this.dh+=-8.4 ;this.ds+=-22.4;return;}
//		if (bases.equals("AG")) {this.dh+=-7.8 ;this.ds+=-21.0;return;}
//		if (bases.equals("TC")) {this.dh+=-8.2 ;this.ds+=-22.2;return;}
//		if (bases.equals("CC")) {this.dh+=-8.0 ;this.ds+=-19.9;return;}
//	}
	
	private double[] getDH_DS(String bases) {
		// assumes that the lenght of bases is 2 and that bases is uppercase
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Is not the most efficient way, but is easy-to-read.
		double dh=0;
		double ds=0;
		if (bases.equals("AA")) { dh=-7.9 ; ds=-22.2; }
		if (bases.equals("AT")) { dh=-7.2 ; ds=-20.4; }
		if (bases.equals("TA")) { dh=-7.2 ; ds=-21.3; }
		if (bases.equals("CA")) { dh=-8.5 ; ds=-22.7; }
		if (bases.equals("GT")) { dh=-8.4 ; ds=-22.4; }
		if (bases.equals("CT")) { dh=-7.8 ; ds=-21.0; }
		if (bases.equals("GA")) { dh=-8.2 ; ds=-22.2; }
		if (bases.equals("CG")) { dh=-10.6; ds=-27.2; }
		if (bases.equals("GC")) { dh=-9.8 ; ds=-24.4; }
		if (bases.equals("GG")) { dh=-8.0 ; ds=-19.9; }
		if (bases.equals("TT")) { dh=-7.9 ; ds=-22.2; }
		if (bases.equals("TG")) { dh=-8.5 ; ds=-22.7; }
		if (bases.equals("AC")) { dh=-8.4 ; ds=-22.4; }
		if (bases.equals("AG")) { dh=-7.8 ; ds=-21.0; }
		if (bases.equals("TC")) { dh=-8.2 ; ds=-22.2; }
		if (bases.equals("CC")) { dh=-8.0 ; ds=-19.9; }
		return new double[] {dh,ds};
	}
	
//	private void addToDH_DS_Deg(String degbases, int degValue) {
//		// assumes that the lenght of bases is 2 and that bases is uppercase
//		// DH values are in Kcal/mol, while DS values are in cal/mol
//		// Is not the most efficient way, but is easy-to-read.
//		
//		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(degbases);
//		dpi.start();
//		
//		while (dpi.hasNext()) {
//			String bases = dpi.next();
//			if (bases.equals("AA")) {this.dh+=-7.9/degValue;this.ds+=-22.2/degValue;}
//			if (bases.equals("AT")) {this.dh+=-7.2/degValue;this.ds+=-20.4/degValue;}
//			if (bases.equals("TA")) {this.dh+=-7.2/degValue;this.ds+=-21.3/degValue;}
//			if (bases.equals("CA")) {this.dh+=-8.5/degValue;this.ds+=-22.7/degValue;}
//			if (bases.equals("GT")) {this.dh+=-8.4/degValue;this.ds+=-22.4/degValue;}
//			if (bases.equals("CT")) {this.dh+=-7.8/degValue;this.ds+=-21.0/degValue;}
//			if (bases.equals("GA")) {this.dh+=-8.2/degValue;this.ds+=-22.2/degValue;}
//			if (bases.equals("CG")) {this.dh+=-10.6/degValue;this.ds+=-27.2/degValue;}
//			if (bases.equals("GC")) {this.dh+=-9.8/degValue;this.ds+=-24.4/degValue;}
//			if (bases.equals("GG")) {this.dh+=-8.0/degValue;this.ds+=-19.9/degValue;}
//			if (bases.equals("TT")) {this.dh+=-7.9/degValue;this.ds+=-22.2/degValue;}
//			if (bases.equals("TG")) {this.dh+=-8.5/degValue;this.ds+=-22.7/degValue;}
//			if (bases.equals("AC")) {this.dh+=-8.4/degValue;this.ds+=-22.4/degValue;}
//			if (bases.equals("AG")) {this.dh+=-7.8/degValue;this.ds+=-21.0/degValue;}
//			if (bases.equals("TC")) {this.dh+=-8.2/degValue;this.ds+=-22.2/degValue;}
//			if (bases.equals("CC")) {this.dh+=-8.0/degValue;this.ds+=-19.9/degValue;}
//		}
//		return;
//	}
	
	private double[] getDH_DS_Deg(String degDinucleotide) {
		// assumes that the lenght of bases is 2 and that bases is uppercase
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Is not the most efficient way, but is easy-to-read.
		
		int degValue = BaseDeg.getDegValueFromString(degDinucleotide);
		
		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(degDinucleotide);
		dpi.start();
		
		double dh = 0;
		double ds = 0;
		
		while (dpi.hasNext()) {
			String bases = dpi.next();
			double[] dhds = this.getDH_DS(bases);
			dh += dhds[0];
			ds += dhds[1];
		}
		return new double[] {dh/degValue,ds/degValue};
	}
	
//	private void addEndsToDH_DS	(char end5,char end3) {
//		// DH values are in Kcal/mol, while DS values are in cal/mol
//		// Assumes that end5 & end3 are uppercase
//		char[] ends = new char[]{end5,end3};
//
//		for (char c : ends) {
//			switch(c) {
//				case 'A': case 'T': this.dh+=2.3;this.ds+=4.1;break;
//				case 'C': case 'G': this.dh+=0.1;this.ds+=-2.8;break;
//			}
//		}
//	}
	
	private double[] getEndPenalty_DH_DS (char end) {
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Assumes that end5 & end3 are uppercase
		double dh = 0;
		double ds =0;
		switch(end) {
			case 'A': case 'T': dh+=2.3;ds+= 4.1;break;
			case 'C': case 'G': dh+=0.1;ds+=-2.8;break;
		}
		return new double[]{dh,ds};
	}
	
//	private void addEndsToDH_DS_DEG	(char end) {
//		// DH values are in Kcal/mol, while DS values are in cal/mol
//		// Assumes that end5 & end3 are uppercase
//		
//		char[] ends = new char[BaseDeg.getDegValueFromChar(end)];
//		
//		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(String.valueOf(end));
//		dpi.start();
//
//		int counter=0;
//		while (dpi.hasNext()) {
//			ends[counter] = dpi.next().charAt(0);
//			counter++;
//		}
//
//		for (char c : ends) {
//			switch(c) {
//				case 'A': case 'T': this.dh+=2.3/counter;this.ds+=4.1/counter;break;
//				case 'C': case 'G': this.dh+=0.1/counter;this.ds+=-2.8/counter;break;
//			}
//		}
//	}
	
	private double[] getEndPenaltiesDH_DS_DEG	(char end) {
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Assumes that end5 & end3 are uppercase
		
		char[] ends = new char[BaseDeg.getDegValueFromChar(end)];
		
		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(String.valueOf(end));
		dpi.start();

		int counter=0;
		while (dpi.hasNext()) {
			ends[counter] = dpi.next().charAt(0);
			counter++;
		}

		double dh = 0;
		double ds = 0;
		for (char c : ends) {
			double[] dhds = getEndPenalty_DH_DS(c);
			dh+= dhds[0];
			ds+= dhds[1];
		}
		return new double[]{dh/counter,ds/counter};
	}

//	private void saltCorrectedDS(double M_Na, int len) {
//		// M_Na is the molar concentration of Na+ (or other monovalente  ions)
//		this.dsc =  (double ) (this.ds + 0.368 * (len-1) * Math.log(M_Na));
//	}

	private double saltCorrectedDS(double M_Na, int len, double ds) {
		// M_Na is the molar concentration of Na+ (or other monovalente  ions)
		return (double ) (ds + 0.368d * (len-1) * Math.log(M_Na));
	}

	@SuppressWarnings("unused")
	private double calculateMaxTM(String degSequence) {
		double[][] dsValues = new double[degSequence.length()-1][16];

		// Dead End!!!
		// hacer tres matrices con valores..
		// Una para los valores de dh,
		// Otra para los valores de ds
		// otra para los valores de tM.
		// buscar el m�ximo por medio del tM.
		// Hacer despu�s del resto!
		// p�ede llevar bastante tiempo.
		
		for (double[] ds : dsValues) { for (int i = 0; i < ds.length; i++) ds[i] = 0; }
			// initialize Matrixes
		
			// Complete matrix o values
				// Fill first row
			    // In first row is possible to add the penalty value 3'.
			
				String degDinucleotide = degSequence.substring(0, 1);

				DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(degDinucleotide);
				dpi.start();
				
				while (dpi.hasNext()) {
					String dinucleotide = dpi.next();
					int index = this.getdnIndex(dinucleotide);
					double[] dhds = this.getDH_DS(dinucleotide);
					dsValues[0][index] = dhds[1] + this.getEndPenalty_DH_DS(dinucleotide.charAt(0))[1];
				}
				
			for (int i=1;i<degSequence.length()-1;i++) {			
				// from second row
					
				// for each possible dinucleotide.
				degDinucleotide = degSequence.substring(i, i+1);
				dpi = new DegeneratedPrimerIterator(degDinucleotide);
				dpi.start();
				
				while (dpi.hasNext()) {
					String dinucleotide = dpi.next();
					int index = this.getdnIndex(dinucleotide);
					double currentDs = this.getDH_DS(dinucleotide)[1];
					double minValue = 0;

			        // From the compatible previous dinucleotides, look the lower value
					
					for (int j=0; j<16;j++) {
						int pI = j / 4;
						int cI = index % 4;
						
						if (pI==cI) /* if equal, are compatible */ {
							minValue = Math.min(dsValues[i-1][j], minValue);
						}
					}
					dsValues[i][index] = minValue + currentDs;
				}
		}

		double minValue = 0;
		for (int j=0; j<16;j++) {
			double penalty = this.getEndPenalty_DH_DS(degSequence.charAt(degSequence.length()-1))[1];
				// add penalties values for 5'
			minValue = Math.min(dsValues[degSequence.length()-2][j] + penalty, minValue);
		}
		return minValue;
	}

	private int getdnIndex(String dinucleotide) {
		char[] dn = new char[] {dinucleotide.charAt(0), dinucleotide.charAt(1)};
		int v =0;
		for (int i = 0; i<2;i++) {
			
			int b = 0;
			switch (Character.toUpperCase(dn[i])) {
			case 'A': b = 0 ; break;
			case 'C': b = 1 ; break;
			case 'T': b = 2 ; break;
			case 'G': b = 3 ; break;
			}
			v= v + b * 4^(i);
			
		}
		return v ;
	}
	
	public static void main(String[] arg) {
		SantaluciaTmEstimator ste = new SantaluciaTmEstimator();
		
		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator("NNNNNNNNNAAAAAAAAA");
		dpi.start();

		double mean = 0;
		double counter = 0;
		while (dpi.hasNext()){
			String s = dpi.next();
			Primer p = new Primer(s, "a", 1, 5, true);
			ste.calculateTM(p);
			mean += ste.mean();
			counter++;
		}
		
		System.out.println(mean/counter);
		System.out.println(counter);
		
		System.out.println(ste.calculateDeg("NNNNNNNNNAAAAAAAAA"));
		
		ste.calculateTM(new Primer("NNNNNNNAAAAAAAAAAA", "x", 1, 20, true));
		System.out.println(ste.mean());
		
		
		
		
	}
}
