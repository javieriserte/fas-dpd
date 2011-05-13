package fasdpd.UI.v1.filterCreators;
import sequences.util.tmcalculator.SantaluciaTmEstimator;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterCGContent;
import filters.singlePrimer.FilterMeltingPointTemperature;
public class FilterMeltingPointTemperatureCreator extends FilterCreator{
	public FilterMeltingPointTemperatureCreator() {
		parametersComments = new String[] {"min Value:","max Value:"};
		parametersTypes = new ParameterType[] { new DoubleParameter(),new DoubleParameter() };
		parametersValues = new String[] {"50","60"};
	}
	@Override protected Filter create(){ 
		Double p0=null;
		Double p1=null;
		try {
			p0 = (Double) parametersTypes[0].parseParameter(parametersValues[0]);
			p1 = (Double) parametersTypes[1].parseParameter(parametersValues[1]);				
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterMeltingPointTemperature(p0,p1, new SantaluciaTmEstimator()); // TODO use default TME
	}
} 
