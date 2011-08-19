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

package fasdpd;

import java.util.List;
import java.util.Vector;

import degeneration.GeneticCode;
import filters.validator.PrimerPairValidable;
import filters.validator.PrimerValidable;
import filters.validator.Validator;
import sequences.dna.DNASeq;
import sequences.dna.Primer;

/**
 * This class computes scores and search the primers in a sequence.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class Analyzer {
	private float pA;
	private float Ny;
	private float Nx;
	private GeneticCode gc;

	// CONSTRUCTOR
	/**
	 * Creates a new instance of Analyzer with standard parameters.
	 * 
	 */
	public Analyzer(GeneticCode myGeneticCode) {
		this.Nx= 1;
		this.Ny= 1;
		this.pA= 0;
		this.gc = myGeneticCode;
	}
	/**
	 * Creates a new instance of Analyzer with custom parameters.
	 * 
	 */
	public Analyzer(float pA, float Ny, float Nx, GeneticCode myGeneticCode) {
		this.Nx= Nx;
		this.Ny= Ny;
		this.pA= pA;
		this.gc = myGeneticCode;
	}
	
	
	// PUBLIC METHODS
	/**
	 * Search primers that have higher scores.
	 * 
	 * @param numberOfPrimers is the number of primers that are stored in the result.
	 * @param DNASeq is the sequence where the primers will be searched.
	 * @param primerLength is the length of desired primers.
	 * @param directStrand indicates that the sequence of primers will be search in the complementary strand of DNASeq.
	 * @param Filter is a Validator that checks if the primers meet any additional requirements.
	 * @param StartPoint is the position of DNAseq where the search start.
	 * @param EndPoint is the position of DNAseq where the search Ends. If EndPoint == -1 the search will be done until the end of the sequence is reached 
	 */
	public PriorityList<Primer> searchBestPrimers(int numberOfPrimers, DNASeq seq, int primerLength, boolean directStrand, Validator Filter, int StartPoint, int EndPoint) {
		// PRECONDITION  : StartPoint is equal o greater than one and is lesser than (sequence length - primer length)
		//               : End point is greater than startpoint plus primer length and lesser than sequence length. 
		PriorityList<Primer> lp = new PriorityList<Primer>(numberOfPrimers);
		
		DNASeq mySeq=null;
		
		if (directStrand) {mySeq = seq;} else {mySeq=(DNASeq) seq.getReverseComplementary();}
		
		if (EndPoint==-1) {
			// EndPoint == -1 means that the end of the search coincides with the end of the sequence
			EndPoint = seq.getLength();
		}  
		
		for (int x=StartPoint-1; x<EndPoint-primerLength+1; x++) {
			Primer p = mySeq.designPrimer(x+1, x+primerLength, directStrand);
				
			p.setScore(this.calculatePrimerScore(p));
			if (Filter.validate( new PrimerValidable(p))) {
				lp.addValue(p);
			}
			
		}
		
		return lp;
	}
	
	public PriorityList<Primer> searchBestPrimers(int numberOfPrimers, DNASeq seq, int primerLengthMin, int primerLengthMax, boolean directStrand, Validator Filter, int StartPoint, int EndPoint) {
		// PRECONDITION  : StartPoint is equal o greater than one and is lesser than (sequence length - primer length)
		//               : End point is greater than startpoint plus primer length and lesser than sequence length. 
		
		PriorityList<Primer> lp = new PriorityList<Primer>(numberOfPrimers);
		PriorityList<Primer> lppartial = new PriorityList<Primer>(numberOfPrimers);
		
		for (int i = primerLengthMin; i<=primerLengthMax;i++) {
			lppartial = this.searchBestPrimers(numberOfPrimers, seq, i, directStrand, Filter, StartPoint, EndPoint);
			
			for (Primer primer: lppartial.ExtractSortedList()) {
				lp.addValue(primer);
			}
			
		}
		
		return lp;
	}
	

	/**
	 * Search compatible primers.
	 * This method can be expensive, if the input list are large.
	 *  
	 * @param forward is a list of candidate forward primers 
	 * @param reverse is a list of candidate reverse primers
	 * @param filter gives the selection conditions.
	 * @return a list of pairs of compatible primers. 
	 */
	public List<PrimerPair> searchPrimerPairs(List<Primer> forward, List<Primer> reverse, Validator filter) {
		List<PrimerPair> result = new Vector<PrimerPair>();
		
		for (Primer primerf : forward) {
			
			for (Primer primerr : reverse) {
				
				PrimerPairValidable ppv = new PrimerPairValidable (primerf,primerr); 
				if (filter.validate(ppv)) result.add(new PrimerPair(primerf,primerr));
				
			}
			
		}
		
		return result;
	}
	
	/**
	 * Calculates a primer score according to:	
	 * <ul><tt>
	 * <li>Eq. 1: Sp_i = 1 - Log_10(NDi)<br>
	 * <li>Eq. 2: Wp_i = pA + i * (Ny - pA) / Nx<br> 
	 * <li>Eq. 3: Ms = n * pA + (n + 1) * n * (Ny - pA) / ( 2 * Nx)<br>
	 * <li>Eq. 4: Sg = Suma(i=1,n,Sp * Wp) / Ms<br>
     * </tt></ul>
     * @param p is a Primer object
     * @return the score for a primer.
	 */
	public float calculatePrimerScore(Primer p) {

		float sum =0;
		for (int i=0;i<p.getLength();i++) {
			char b = p.getSequence().charAt(i);
			sum = sum + this.Sp(gc.calculateDegValue(b))*this.Wp(i+1);
		}
		return sum/this.Ms(p.getLength());
		
	}
	
	// PRIVATE AND PROTECTED METHODS
	private float Sp(int NDi) {
		return (float) (1 - Math.log10(NDi));
	}
	
	private float Wp(int i) {
		return this.pA + i * (this.Ny - this.pA) / this.Nx;
	}
	
	private float Ms(int n) {
		return n * this.pA + (n + 1) * n * (this.Ny - this.pA) / ( 2 * this.Nx);
	}
}
