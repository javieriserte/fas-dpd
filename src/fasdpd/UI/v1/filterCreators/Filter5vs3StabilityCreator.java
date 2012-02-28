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

package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.Filter5vs3Stability;
import filters.singlePrimer.FilterCGContent;


/**
 * @author Javi
 *
 */
public class Filter5vs3StabilityCreator extends FilterCreator{
	public Filter5vs3StabilityCreator() {
		parametersComments = new String[] {"max difference:","KelvinTemp:","Length:", "Monovalent Ion Conc. (mM):"};
		parametersTypes = new ParameterType[] { new DoubleParameter(),new DoubleParameter(), new IntegerParameter(), new DoubleParameter()};
		parametersValues = new String[] {"1.5", "310","5","50"};
	}
	
	@Override public Filter create() {
		Double p0=null;
		Double p1=null;
		Integer p2=null;
		Double p3=null;
		
		try {
			p0 = (Double) parametersTypes[0].parseParameter(parametersValues[0]);
			p1 = (Double) parametersTypes[1].parseParameter(parametersValues[1]);
			p2 = (Integer) parametersTypes[2].parseParameter(parametersValues[2]);
			p3 = (Double) parametersTypes[3].parseParameter(parametersValues[3]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new Filter5vs3Stability(p0,p1,p3,p2);
	}
	
	@Override public String toString() { return "Single Primer - Keep Primers With (5' - 3') Stability Greater than " + parametersValues[0] + " KJ"; }
} 
