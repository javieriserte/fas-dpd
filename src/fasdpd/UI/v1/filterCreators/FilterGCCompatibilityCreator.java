package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterGCCompatibility;
import filters.singlePrimer.FilterCGContent;
public class FilterGCCompatibilityCreator extends FilterCreator{
	public FilterGCCompatibilityCreator() {
		parametersComments = new String[] {"max Difference:"};
		parametersTypes = new ParameterType[] { new DoubleParameter()};
		parametersValues = new String[] {"10"};
	}
	
	@Override public Filter create() {
		Double p0=null;

		try {
			p0 = (Double) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterGCCompatibility(p0);
	}
	@Override public String toString() { return "Primer Pair - Keep Primer Pairs Whose Difference In G+C Content Is Less Than "+ this.parametersValues[0]+"%";}	
} 
