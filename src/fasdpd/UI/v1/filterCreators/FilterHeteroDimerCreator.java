package fasdpd.UI.v1.filterCreators;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterHeteroDimer;
import filters.singlePrimer.FilterCGContent;
public class FilterHeteroDimerCreator extends FilterCreator{
	public FilterHeteroDimerCreator() {
		parametersComments = new String[] {"max Value:"};
		parametersTypes = new ParameterType[] { new IntegerParameter() };
		parametersValues = new String[] {"6"};
	}
	
	@Override public Filter create() {
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterHeteroDimer(p0, new DegeneratedDNAMatchingStrategy());
	}
	@Override public String toString() { return "Filter Hetero Dimer larger than" + this.parametersValues[0];}	
} 
