package fasdpd.UI.v1.filterCreators;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterCGContent;
import filters.singlePrimer.FilterHomoDimer;
public class FilterHomoDimerCreator extends FilterCreator{
	public FilterHomoDimerCreator() {
		parametersComments = new String[] {"max Value:"};
		parametersTypes = new ParameterType[] { new IntegerParameter() };
		parametersValues = new String[] {"5"};
	}
	
	@Override public Filter create() {
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterHomoDimer(p0,new DegeneratedDNAMatchingStrategy());
	}
	@Override public String toString() { return "Filter Homo Dimer larger than" + this.parametersValues[0];}	
} 
