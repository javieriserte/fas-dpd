package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterCGContent;
import filters.singlePrimer.FilterPrimerScore;
public class FilterPrimerScoreCreator extends FilterCreator{
	public FilterPrimerScoreCreator() {
		parametersComments = new String[] {"min Value:"};
		parametersTypes = new ParameterType[] { new DoubleParameter() };
		parametersValues = new String[] {"0.8"};
	}
	
	@Override public Filter create() {
		Double p0=null;
		try {
			p0 = (Double) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterPrimerScore(p0);
	}
	@Override public String toString() { return "Filter score smaller than "+ this.parametersValues[0];}	
} 
