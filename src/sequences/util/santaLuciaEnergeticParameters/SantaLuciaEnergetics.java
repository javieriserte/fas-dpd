/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

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
			current = this.getNearestNeighbor(sequence.substring(i-1, i+1));
			total.add(current);
		}
		total.setDeltaGFromDeltaHAndDeltaS(kelvinTemp);
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
		ev.setDeltaGFromDeltaHAndDeltaS(kelvinTemp);
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
				case 'A': case 'T': ev.setDeltaH(+ 2.3) ; ev.setDeltaS( + 4.1) ; break;
				case 'C': case 'G': ev.setDeltaH(+ 0.1 ); ev.setDeltaS( - 2.8) ; break;
		}
		
		ev.setDeltaGFromDeltaHAndDeltaS(310);
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
		
		if (dinucleotide.equals("AA")) {ev.setDeltaH( -  7.9 ) ; ev.setDeltaS( - 22.2 ) ; return ev ; }
		if (dinucleotide.equals("AT")) {ev.setDeltaH( -  7.2 ) ; ev.setDeltaS( - 20.4 ) ; return ev ; }
		if (dinucleotide.equals("AC")) {ev.setDeltaH( -  8.4 ) ; ev.setDeltaS( - 22.4 ) ; return ev ; }
		if (dinucleotide.equals("AG")) {ev.setDeltaH( -  7.8 ) ; ev.setDeltaS( - 21.0 ) ; return ev ; }
		if (dinucleotide.equals("CA")) {ev.setDeltaH( -  8.5 ) ; ev.setDeltaS( - 22.7 ) ; return ev ; }
		if (dinucleotide.equals("CC")) {ev.setDeltaH( -  8.0 ) ; ev.setDeltaS( - 19.9 ) ; return ev ; }
		if (dinucleotide.equals("CT")) {ev.setDeltaH( -  7.8 ) ; ev.setDeltaS( - 21.0 ) ; return ev ; }
		if (dinucleotide.equals("CG")) {ev.setDeltaH( - 10.6 ) ; ev.setDeltaS( - 27.2 ) ; return ev ; }
		if (dinucleotide.equals("TA")) {ev.setDeltaH( -  7.2 ) ; ev.setDeltaS( - 21.3 ) ; return ev ; }
		if (dinucleotide.equals("TC")) {ev.setDeltaH( -  8.2 ) ; ev.setDeltaS( - 22.2 ) ; return ev ; }
		if (dinucleotide.equals("TT")) {ev.setDeltaH( -  7.9 ) ; ev.setDeltaS( - 22.2 ) ; return ev ; }
		if (dinucleotide.equals("TG")) {ev.setDeltaH( -  8.5 ) ; ev.setDeltaS( - 22.7 ) ; return ev ; }
		if (dinucleotide.equals("GA")) {ev.setDeltaH( -  8.2 ) ; ev.setDeltaS( - 22.2 ) ; return ev ; }
		if (dinucleotide.equals("GC")) {ev.setDeltaH( -  9.8 ) ; ev.setDeltaS( - 24.4 ) ; return ev ; }
		if (dinucleotide.equals("GT")) {ev.setDeltaH( -  8.4 ) ; ev.setDeltaS( - 22.4 ) ; return ev ; }
		if (dinucleotide.equals("GG")) {ev.setDeltaH( -  8.0 ) ; ev.setDeltaS( - 19.9 ) ; return ev ; }
		
		return null;
		// TODO raise exception if code reachs 'return null' line
	}
	
	public EnergeticValues SaltCorrection(double monovalent, double divalent, EnergeticValues originalEnergetics, int sequenceLength) {
		// monovalent is the molar concentration of Na+ (or other monovalente  ions)
		// divalent is not used by now. Some work transform divalent into monovalent, buy original SantaLucia's paper don't.
		
		EnergeticValues result = new EnergeticValues();

		result.setDeltaH(originalEnergetics.getDeltaH());
		result.setDeltaS((double) (originalEnergetics.getDeltaS() + 0.368 * (sequenceLength-1) * Math.log(monovalent)));
		result.setDeltaG((double) (originalEnergetics.getDeltaG() - 0.114 * (sequenceLength-1) * Math.log(monovalent)));
		
		return result;
		
	}
	
}
