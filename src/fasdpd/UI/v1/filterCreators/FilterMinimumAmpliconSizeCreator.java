package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterAmpliconSize;
import filters.primerpair.FilterMinimumAmpliconSize;

public class FilterMinimumAmpliconSizeCreator extends FilterCreator{
	public FilterMinimumAmpliconSizeCreator() {
		parametersComments = new String[] {"min Length:"};
		parametersTypes = new ParameterType[] {
		  new IntegerParameter()
		};
		parametersValues = new String[] {"80"};
	}
	
	@Override public Filter create() {
		Integer p0=null;
		try {
			p0 = (Integer) parametersTypes[0].parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterMinimumAmpliconSize(p0);
	}
	@Override public String toString() {
	  return "Primer Pair - Remove Primers With An Amplicon Size Smaller Than " +
	  this.parametersValues[0] + " bp.";
	}
} 
