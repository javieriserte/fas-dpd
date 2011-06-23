package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterBaseRuns;
import filters.singlePrimer.FilterCGContent;
public class FilterBaseRunsCreator extends FilterCreator{
	public FilterBaseRunsCreator() {
		parametersComments = new String[] {"max Length:"};
		parametersTypes = new ParameterType[] { new IntegerParameter()};
		parametersValues = new String[] {"4"};
	}
	
	@Override public Filter create() {
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterBaseRuns(p0);
	}
	@Override public String toString() { return "Filter Runs of bases larger than "+ this.parametersValues[0]; }
	
} 
