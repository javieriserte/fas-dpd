package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.Filter5vs3Stability;
import filters.singlePrimer.FilterCGContent;


/**
 * @author Javi
 *
 */
public class Filter5vs3StabilityCreator extends FilterCreator{
	public Filter5vs3StabilityCreator() {
		parametersComments = new String[] {"max difference:","KelvinTemp:","Length:", "Monovalent Ion Conc. (mM):"};
		parametersTypes = new ParameterType[] { new DoubleParameter(),new DoubleParameter(), new IntegerParameter(), new DoubleParameter()};
		parametersValues = new String[] {"1.5", "310","5","50"};
	}
	
	@Override public Filter create() {
		Double p0=null;
		Double p1=null;
		Integer p2=null;
		Double p3=null;
		
		try {
			p0 = (Double) parametersTypes[0].parseParameter(parametersValues[0]);
			p1 = (Double) parametersTypes[1].parseParameter(parametersValues[1]);
			p2 = (Integer) parametersTypes[2].parseParameter(parametersValues[2]);
			p3 = (Double) parametersTypes[3].parseParameter(parametersValues[3]);
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new Filter5vs3Stability(p0,p1,p3,p2);
	}
	
	@Override public String toString() { return "Filter Stability of 5' vs 3'"; }
} 
