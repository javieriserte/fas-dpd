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

package fasdpd.UI.v1.filterCreators;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cmdGA.parameterType.ParameterType;
import filters.Filter;

public abstract class FilterCreator {
	String[] parametersComments= null;
	ParameterType[] parametersTypes= null;
	String[] parametersValues= null;
	
	ComponentWrapper[] components = null;
	
	public abstract Filter create();
	
	public String[] getParametersComments() {
		return parametersComments;
	}
	public ParameterType[] getParametersTypes() {
		return parametersTypes;
	}
	public String[] getParametersValues() {
		return parametersValues;
	}
	
	public JPanel getCreationPanel() {
		JPanel creationPanel = new JPanel();


		GridLayout gl = new GridLayout();
		gl.setColumns(1);
		creationPanel.setLayout(gl);
		int len = 0;
		if (parametersComments != null) {
			len = parametersComments.length + 1;
			this.components = new ComponentWrapper[len-1];			
		}
		
		gl.setRows(Math.max(1, len-1));
		for(int i= Math.min(1, len) ;i < Math.max(1,len);i++) {
			creationPanel.add(this.option(i));
		}
		return creationPanel;
	}
	
	public FilterCreator duplicateWithGUIvalues() {
		FilterCreator fc = null;
		try {
			fc = this.getClass().newInstance();
			fc.components = null;
			fc.parametersComments = this.parametersComments != null ? this.parametersComments.clone(): null;
			fc.parametersTypes = this.parametersTypes != null ? this.parametersTypes.clone(): null ;
			fc.parametersValues = this.parametersValues != null ? new String[fc.parametersComments.length]: null;
			
			if (fc.parametersValues!= null) {
				for (int i=0;i<fc.parametersComments.length;i++) {
					fc.parametersValues[i] = (String) this.components[i].getValue();
				}
			}
			
		} catch (InstantiationException e) { e.printStackTrace(); } 
		  catch (IllegalAccessException e) { e.printStackTrace(); }
		
		
		return fc;
	}
	
	private JPanel option(int index) {
		String comment;
		String value;
		JPanel jl = new JPanel();
		if (index>0) {
			comment = this.getParametersComments()[index-1];
			value = this.getParametersValues()[index-1];
			JTextFieldWrapper t = new JTextFieldWrapper(new JTextField(value));
			
	
			this.components[index-1] = t;
			jl.add(new JLabel(comment));
			jl.add(t.component);

		} else {
			jl.add(new JLabel("No config Needed"));
		}
		return jl;
	}
	
}
