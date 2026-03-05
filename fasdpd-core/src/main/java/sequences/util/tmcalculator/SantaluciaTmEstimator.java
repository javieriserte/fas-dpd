package sequences.util.tmcalculator;

import java.util.stream.IntStream;

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
		if (degValue <= 1) {
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

	@Override
	public double mean() {
		return this.mean;
	}

	@Override
	public double max() {
		return this.max;
	}

	@Override
	public double min() {
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

		// Adds penalties for starting and ending positions
		dhds = this.getEndPenaltiesDH_DS_DEG(seq.charAt(0));
		this.dh += dhds[0];
		this.ds += dhds[1];
		dhds = this.getEndPenaltiesDH_DS_DEG(seq.charAt(seq.length()-1));
		this.dh += dhds[0];
		this.ds += dhds[1];

		// Makes Salt Corrections
		this.dsc = saltCorrectedDS(0.050D,seq.length(),this.ds);
		return getMeltingTemp(this.dh, this.dsc);
	}

	protected double getMeltingTemp(double dh, double ds) {
		return (double) (
			dh /* delta_H in kcal/mol */ *
			(double) 1000 /* to convert kcal to cal */
			/ (ds /* delta_S in cal */ +
			(
				(double) 1.987 /* cal/K*mol */ ) *
				Math.log((double) (50 / 1000000000D)
			) /* Denominator is CT. assumes [50nM] */ )
				/*
				 * For non-selfcomplementary molecules, CT in Eq. 3 is replaced by CT/4 if the
				 * strands are in equal concentration or by (CA - CB/2) if the strands are at
				 * different concentrations, where CA and CB are the concentrations of the more
				 * concentrated and less concentrated strands, respectively
				 */
				- (double) 273.15); /* Kelvin to Celcius */
	}

	private void calcNearestNeighborEnergetics(String seq) {
		IntStream.range(1, seq.length()).forEach(i -> {
			String dinucleotide = seq.substring(i-1, i+1);
			double[] dhds = getDH_DS(dinucleotide);
			this.dh += dhds[0];
			this.ds += dhds[1];
		});
	}

	private double calculate(String seq) {
		resetEnergetics();
		calcNearestNeighborEnergetics(seq);
		calcEndPenalties(seq);
		this.dsc = saltCorrectedDS(
			0.050D,
			seq.length(),
			this.ds
		);
		// Makes Salt Corrections
		return getMeltingTemp(
			this.dh,
			this.dsc
		);
	}

	private void calcEndPenalties(String seq) {
		char firstBase = seq.charAt(0);
		double[] dhds = this.getEndPenalty_DH_DS(firstBase);
		this.dh += dhds[0];
		this.ds += dhds[1];
		char lastBase = seq.charAt(seq.length() - 1);
		dhds = this.getEndPenalty_DH_DS(lastBase);
		this.dh += dhds[0];
		this.ds += dhds[1];
	}

	private void resetEnergetics() {
		this.dh = 0;
		this.ds = 0;
		this.dsc = 0;
	}

	private double[] getDH_DS(String bases) {
		// assumes that the lenght of bases is 2 and that bases is uppercase
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Is not the most efficient way, but is easy-to-read.
		double dh = 0;
		double ds = 0;
		if (bases.equals("AA")) {
			dh = -7.9;
			ds = -22.2;
		}
		if (bases.equals("AT")) {
			dh = -7.2;
			ds = -20.4;
		}
		if (bases.equals("TA")) {
			dh = -7.2;
			ds = -21.3;
		}
		if (bases.equals("CA")) {
			dh = -8.5;
			ds = -22.7;
		}
		if (bases.equals("GT")) {
			dh = -8.4;
			ds = -22.4;
		}
		if (bases.equals("CT")) {
			dh = -7.8;
			ds = -21.0;
		}
		if (bases.equals("GA")) {
			dh = -8.2;
			ds = -22.2;
		}
		if (bases.equals("CG")) {
			dh = -10.6;
			ds = -27.2;
		}
		if (bases.equals("GC")) {
			dh = -9.8;
			ds = -24.4;
		}
		if (bases.equals("GG")) {
			dh = -8.0;
			ds = -19.9;
		}
		if (bases.equals("TT")) {
			dh = -7.9;
			ds = -22.2;
		}
		if (bases.equals("TG")) {
			dh = -8.5;
			ds = -22.7;
		}
		if (bases.equals("AC")) {
			dh = -8.4;
			ds = -22.4;
		}
		if (bases.equals("AG")) {
			dh = -7.8;
			ds = -21.0;
		}
		if (bases.equals("TC")) {
			dh = -8.2;
			ds = -22.2;
		}
		if (bases.equals("CC")) {
			dh = -8.0;
			ds = -19.9;
		}
		return new double[] { dh, ds };
	}

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
		return new double[] { dh / degValue, ds / degValue };
	}

	private double[] getEndPenalty_DH_DS(char end) {
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Assumes that end5 & end3 are uppercase
		double dh = 0;
		double ds = 0;
		switch (end) {
			case 'A':
			case 'T':
				dh += 2.3;
				ds += 4.1;
				break;
			case 'C':
			case 'G':
				dh += 0.1;
				ds += -2.8;
				break;
		}
		return new double[] { dh, ds };
	}

	private double[] getEndPenaltiesDH_DS_DEG(char end) {
		// DH values are in Kcal/mol, while DS values are in cal/mol
		// Assumes that end5 & end3 are uppercase
		char[] ends = new char[BaseDeg.getDegValueFromChar(end)];
		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(
			String.valueOf(end)
		);
		dpi.start();
		int counter = 0;
		while (dpi.hasNext()) {
			ends[counter] = dpi.next().charAt(0);
			counter++;
		}
		double dh = 0;
		double ds = 0;
		for (char c : ends) {
			double[] dhds = getEndPenalty_DH_DS(c);
			dh += dhds[0];
			ds += dhds[1];
		}
		return new double[] { dh / counter, ds / counter };
	}

  private double saltCorrectedDS(double M_Na, int len, double ds) {
		// M_Na is the molar concentration of Na+ (or other monovalente ions)
		return (double) (ds + 0.368d * (len - 1) * Math.log(M_Na));
	}

	@SuppressWarnings("unused")
	private double calculateMaxTM(String degSequence) {
		double[][] dsValues = new double[degSequence.length() - 1][16];

		// Dead End!!!
		// hacer tres matrices con valores..
		// Una para los valores de dh,
		// Otra para los valores de ds
		// otra para los valores de tM.
		// buscar el máximo por medio del tM.
		// Hacer después del resto!
		// puede llevar bastante tiempo.

		for (double[] ds : dsValues) {
			for (int i = 0; i < ds.length; i++)
				ds[i] = 0;
		}
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

		for (int i = 1; i < degSequence.length() - 1; i++) {
			// from second row
			// for each possible dinucleotide.
			degDinucleotide = degSequence.substring(i, i + 1);
			dpi = new DegeneratedPrimerIterator(degDinucleotide);
			dpi.start();
			while (dpi.hasNext()) {
				String dinucleotide = dpi.next();
				int index = this.getdnIndex(dinucleotide);
				double currentDs = this.getDH_DS(dinucleotide)[1];
				double minValue = 0;
				// From the compatible previous dinucleotides, look the lower value
				for (int j = 0; j < 16; j++) {
					int pI = j / 4;
					int cI = index % 4;
					if (pI == cI) /* if equal, are compatible */ {
						minValue = Math.min(dsValues[i - 1][j], minValue);
					}
				}
				dsValues[i][index] = minValue + currentDs;
			}
		}
		double minValue = 0;
		for (int j = 0; j < 16; j++) {
			double penalty = this.getEndPenalty_DH_DS(
				degSequence.charAt(degSequence.length() - 1)
			)[1];
			// add penalties values for 5'
			minValue = Math.min(
				dsValues[degSequence.length() - 2][j] + penalty,
				minValue
			);
		}
		return minValue;
	}

	private int getdnIndex(String dinucleotide) {
		char[] dn = new char[] { dinucleotide.charAt(0), dinucleotide.charAt(1) };
		int v = 0;
		for (int i = 0; i < 2; i++) {
			int b = 0;
			switch (Character.toUpperCase(dn[i])) {
				case 'A':
					b = 0;
					break;
				case 'C':
					b = 1;
					break;
				case 'T':
					b = 2;
					break;
				case 'G':
					b = 3;
					break;
			}
			v = v + b * 4 ^ (i);
		}
		return v;
	}

	public static void main(String[] arg) {
		SantaluciaTmEstimator ste = new SantaluciaTmEstimator();

		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator("NNNNNNNNNAAAAAAAAA");
		dpi.start();

		double mean = 0;
		double counter = 0;
		while (dpi.hasNext()) {
			String s = dpi.next();
			Primer p = new Primer(s, "a", 1, 5, true);
			ste.calculateTM(p);
			mean += ste.mean();
			counter++;
		}

		System.out.println(mean / counter);
		System.out.println(counter);

		System.out.println(ste.calculateDeg("NNNNNNNNNAAAAAAAAA"));

		ste.calculateTM(new Primer("NNNNNNNAAAAAAAAAAA", "x", 1, 20, true));
		System.out.println(ste.mean());

	}
}
