package fasdpd.UI.v1.filterCreators;

import java.awt.Component;

import javax.swing.JTextField;

public class JTextFieldWrapper extends ComponentWrapper {


	public JTextFieldWrapper(Component c) { super(c); }

	@Override public Object getValue() {
		return ((JTextField)this.component).getText();
	}

}
