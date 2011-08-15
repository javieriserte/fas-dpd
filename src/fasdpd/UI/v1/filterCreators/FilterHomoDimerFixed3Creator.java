package fasdpd.UI.v1.filterCreators;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterCGContent;
import filters.singlePrimer.FilterHomoDimerFixed3;
public class FilterHomoDimerFixed3Creator extends FilterCreator{
	public FilterHomoDimerFixed3Creator() {
		parametersComments = new String[] {"max Value:"};
		parametersTypes = new ParameterType[] { new IntegerParameter() };
		parametersValues = new String[] {"3"};
	}
	
	@Override public Filter create() {
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterHomoDimerFixed3(p0,new DegeneratedDNAMatchingStrategy());
	}
	@Override public String toString() { return "Single Primer - Remove Primers That Form Homodimer Structures, Including The 3' End And Are Larger Than " + this.parametersValues[0] + " Bases.";}	
} 
