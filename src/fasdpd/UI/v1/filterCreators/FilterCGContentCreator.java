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
	
	@Override public Filter create() {
		Float p0=null;
		Float p1=null;
		try {
			p0 = (Float) parametersTypes[0].parseParameter(parametersValues[0]);
			p1 = (Float) parametersTypes[1].parseParameter(parametersValues[1]);				
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 

		return new FilterCGContent(p0,p1);
	}
	@Override public String toString() { return "Single Primer - Keep Primers With G+C Content Between ["+ this.parametersValues[0] + "% - " + this.parametersValues[1]+"%]";}
	
}