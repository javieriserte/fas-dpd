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

package sequences.dna;

import degeneration.BaseDeg;

public class DegeneratedPrimerIterator {
	private String initialSeq;
	private int skipFrom;
	private int max;
	private int counter=0;
	
	private char[][] chars;
	private int[] deg;
	private int[] current;

	// CONSTRUCTOR
	public DegeneratedPrimerIterator(String initialSeq, int skipFrom) {
		super();
		this.initialSeq = initialSeq;
		this.skipFrom = skipFrom;
	}

	public DegeneratedPrimerIterator(String initialSeq) {
		super();
		this.initialSeq = initialSeq;
		this.skipFrom = 0;
	}
	
	// PUBLIC INSTANCE METHODS
	
	public void start() {
		
		this.counter=0;
		this.max = BaseDeg.getDegValueFromString(this.initialSeq);
		if (this.skipFrom==0) this.skipFrom =  this.max;
		
		int pos= this.initialSeq.length();
		
		this.chars = new char[pos][];
		this.deg = new int[pos];
		this.current = new int[pos];

		
		for(int i=0;i<pos;i++) {
			this.chars[i] = BaseDeg.getCharArrayFromChar(this.initialSeq.charAt(i)); 
			this.deg[i] = this.chars[i].length;
			this.current[i] = 0;
		}
		
	}
	
	public String next(){
		StringBuilder result = new StringBuilder(this.initialSeq.length());
		
		// Construct result
		for (int i=0;i < this.current.length;i++) {
			result.append(this.chars[i][this.current[i]]);
		}
		
		// Increase & Update current
		this.current[0]++;
		this.counter++;

		
		if(this.hasNext()) {
			for (int i=0;i < this.current.length;i++) {
				if (this.current[i]==this.deg[i]) {
					this.current[i+1]++;
					this.current[i]=0;
				}
			}
		} 
		
		return result.toString();
	}
	
	public boolean hasNext() {
		return  (this.skipFrom>this.counter) && (this.max>this.counter);
	}
	
	
}
