package fasdpd.UI.v1.filterCreators;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterHeteroDimerFixed3;
import filters.singlePrimer.FilterCGContent;
public class FilterHeteroDimerFixed3Creator extends FilterCreator{
	public FilterHeteroDimerFixed3Creator() {
		parametersComments = new String[] {"max Value:"};
		parametersTypes = new ParameterType[] { new IntegerParameter() };
		parametersValues = new String[] {"3"};
	}
	@Override
	public Filter create(){ 
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterHeteroDimerFixed3(p0,new DegeneratedDNAMatchingStrategy());
	}
	@Override public String toString() { return "Filter Hetero Dimer with 3' fixed larger than" + this.parametersValues[0];}	
	
} 
