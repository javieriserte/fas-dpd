package fasdpd.UI.v1.filterCreators;

import cmdGA.parameterType.ParameterType;
import filters.Filter;

public abstract class FilterCreator {
	String[] parametersComments= null;
	ParameterType[] parametersTypes= null;
	String[] parametersValues= null;
	protected abstract Filter create();
}