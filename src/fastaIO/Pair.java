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


package fastaIO;
/**
 * This class represents a pair of elements.
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.13
 * 
 * @param <First> the first component of the pair.
 * @param <Second> the second component of the pair.
 */
public class Pair <First, Second> {
	private First first;
	private Second second;
   
	/**
	 * Creates a new pair a objects.
	 * @param first the first component of the pair.
	 * @param second the second component of the pair.
	 */
	public Pair( First first, Second second ) {
		this.setFirst(first);
		this.setSecond(second);

	}


	// PUBLIC METHODS
	/**
	 * @return hash code for this object.
	 */
	@Override
	public int hashCode() {
		if (this.getFirst() == null) {
			if (this.getSecond() == null) return 0;
			return this.getSecond().hashCode();
		} else {
			if (this.getSecond() == null) return this.getFirst().hashCode() * 43;
			return this.getFirst().hashCode() * 43 *  this.getSecond().hashCode();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Re - implementation of equals method inherited from object. 
	 */
	public boolean equals( Object object ) {
		if ( this == object ) return true;
    
		if ( object == null ) return false;
    
		if (!object.getClass().equals(this.getClass())) return false;
    
		Pair<First, Second> otherObject = (Pair<First, Second> ) object ;
    
		boolean f = ((otherObject.getFirst() == null  && (this.getFirst() == null )) || otherObject.getFirst().equals(this.getFirst()));
		return f && ((otherObject.getSecond() == null  && (this.getSecond() == null )) || otherObject.getSecond().equals(this.getSecond()));

	}

	/**
	 * This is a string representation of the Pair.
	 */
	@Override
	public String toString() {
		return "[ " + this.getFirst().toString() + " , " + this.getSecond().toString() + " ]";
	}
	
	// GETTERS AND SETTERS
	public First getFirst() {
	   return first;
	}
	public Second getSecond() {
		return second;
	}
	public void setFirst(First first) {
		this.first = first;
	}
	public void setSecond(Second second) {
		this.second = second;
	}
   
} 