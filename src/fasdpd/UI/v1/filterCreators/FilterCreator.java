package fasdpd.UI.v1.filterCreators;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cmdGA.parameterType.ParameterType;
import fasdpd.UI.v1.FiltersSelectionPane;
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
