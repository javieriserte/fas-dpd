package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterAmpliconSize;
public class FilterAmpliconSizeCreator extends FilterCreator{
	public FilterAmpliconSizeCreator() {
		parametersComments = new String[] {"max Length:"};
		parametersTypes = new ParameterType[] { new IntegerParameter() };
		parametersValues = new String[] {"200"};
	}
	
	@Override public Filter create() {
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterAmpliconSize(p0);
	}
	@Override public String toString() { return "Primer Pair - Remove Primers With An Amplicon Size Larger Than "+ this.parametersValues[0] + " bp."; }
} 
