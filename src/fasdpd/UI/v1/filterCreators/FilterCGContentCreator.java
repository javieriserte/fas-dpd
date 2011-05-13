package fasdpd.UI.v1.filterCreators;

import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterCGContent;

public 	 class FilterCGContentCreator extends FilterCreator{
	public FilterCGContentCreator() {
		parametersComments = new String[] {"min Value:","max Value:"};
		parametersTypes = new ParameterType[] { new FloatParameter(),new FloatParameter() };
		parametersValues = new String[] {"40","60"};
	}
	@Override protected Filter create(){ 
		Float p0=null;
		Float p1=null;
		try {
			p0 = (Float) parametersTypes[0].parseParameter(parametersValues[0]);
			p1 = (Float) parametersTypes[1].parseParameter(parametersValues[1]);				
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 

		return new FilterCGContent(p0,p1);
	}
	
}