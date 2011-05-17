package fasdpd.UI.v1.filterCreators;

import java.awt.Component;

public abstract class ComponentWrapper {
	Component component= null;
	
	public ComponentWrapper(Component c) {
		this.component = c;
	}
	
	public abstract Object getValue();
}
