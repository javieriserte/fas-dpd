package sequences.util.tmcalculator;

import sequences.dna.Primer;

public interface TmEstimator {

	public void calculateTM(Primer primer);
	public double  mean();
	public double  max();
	public double  min();
	
	/**
	 * Interface for classes that estimate TM of oligonucleotides
	 *
	 * List of methods:
	 *
	 * 0) Tm (°C) = 2 * AT + 4 * GC
	 *
	 * 1) Bolton and McCarthy, PNAS 84:1390 (1962) as presented in Sambrook, Fritsch and Maniatis, Molecular Cloning, p 11.46 (1989, CSHL Press). 
	 * Tm = 81.5 + 16.6(log10([Na+])) + .41*(%GC) - 600/length,
	 * where [Na+] is the molar sodium concentration, (%GC) is the percent of Gs and Cs in the sequence, and length is the length of the sequence.
	 *
	 *
	 * 2) Computer Program for Calculating the Melting Temperature of Degenerate Oligonucleotides Used in PCR or Hybridization
	 * Haoyuan Chen and Guan Zhu1
	 * There have been several reported algorithms for the determination of Tm of an oligonucleotide duplex, but the most accurate are based on the nearest-neighbor interaction model (1,3,6,7). If the oligonucleotide is not self-complementary, the Tm can be calculated as: 
	 *
	 * Tm (°C) = DH/(DS + R * ln(c/4)) - 273.15 + 16.6 * log [salt] [Eq. 2]
	 *
	 * where DH and DS are the enthalpy and entropy for the helix formation of an oligonucleotide
	 * duplex, respectively; R is the molar gas constant (1.987 cal/°C * mol); c is the total
	 * concen tration of the annealing oligonucleotide; and [salt] is the potassium ion (K+)
	 * concentration in the PCR or sodium ion (Na+) in the hybridization of a blot. The effect
	 * of magnesium ions on the Tm of PCR primers has not been considered here because of the
	 * lack of published thermodynamic data. In a standard protocol, K+ = 50 mM in PCR, while
	 * Na+ = 1.0 M in hybridization of a blot, in which 6M standard saline citrate (SSC) or
	 * sodium chloride sodium phosphate EDTA (SSPE) buffer is usually used for both hybridization
	 * and washings (5). We also empirically assigned c = 50 nM or 0.1 nM to Equation 2 for the
	 * calculation of Tm values of degenerate oligonucleotides used in PCR or hybridization,
	 * which were in good agreement when those oligonucleotides with published thermodynamic
	 * data were tested (6,7). The program, named dPrimer, was written in C++ computer language
	 * and implemented in Macintosh® with a Symantec® C++ compiler (Symantec, Cupertino, CA, USA).
	 * The degenerate DNA sequencing data were read into a graph data structure. All possible
	 * oligonucleotide sequences were then determined by a depth-first search algorithm (4).
	 * Individual Tm values of all oligonucleotides were calculated with Equation 2 using
	 * the nearest-neighbor thermodynamic data determined by Breslauer et al. (2). The output
	 * was given as a range of  Tm values, the mean of  Tm values and the standard deviation (SD).
	 *
	 * 3) A unified view of polymer, dumbbell, and oligonucleotide DNA nearest-neighbor thermodynamics
	 * JOHN SANTALUCIA,JR.
	 *
	 *  Tm =DH°/(DS° + R ln CT ),
	 *
	 *  For non-selfcomplementary molecules, CT in Eq. 3 is replaced by CT/4 if the strands are in equal
     *  concentration or by (CA - CB/2) if the strands are at different concentrations, where CA and
     *  CB are the concentrations of the more concentrated and less concentrated strands, respectively.
	 *
	 *  Sequence               DH° kcal/mol  DS° cal/mol
	 *  AA/TT                  -7.9          -22.2
	 *  AT/TA                  -7.2          -20.4
	 *  TA/AT                  -7.2          -21.3
	 *  CA/GT                  -8.5          -22.7
	 *  GT/CA                  -8.4          -22.4
	 *  CT/GA                  -7.8          -21.0
	 *  GA/CT                  -8.2          -22.2
	 *  CG/GC                 -10.6          -27.2
	 *  GC/CG                  -9.8          -24.4
	 *  GG/CC                  -8.0          -19.9
	 *  Init. w/term. G/C       0.1           -2.8
	 *  Init. w/term. A/T       2.3            4.1
	 *  Symmetry correction       0           -1.4
	 *
	 *  Oligo:
	 *  Salt Correction: DS°(oligomer, [Na+]) = DS°(unified oligomer, 1 M NaCl) + 0.368 * N * ln[Na+]. [8]
	 *  Salt Correction: DG°(oligomer, [Na+]) = DG°(unified oligomer, 1 M NaCl) - 0.114 * N * ln[Na+]. [8]
	 *
	 * 4) Effects of Sodium Ions on DNA Duplex Oligomers: Improved Predictions of Melting Temperatures
	 * Richard Owczarzy, Yong You, Bernardo G. Moreira, Jeffrey A. Manthey, Lingyan Huang, Mark A. Behlke, and Joseph A. Walder
	 */
}
