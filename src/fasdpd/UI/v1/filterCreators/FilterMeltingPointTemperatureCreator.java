package fasdpd.UI.v1.filterCreators;
import sequences.util.tmcalculator.SantaluciaTmEstimator;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterMeltingPointTemperature;
public class FilterMeltingPointTemperatureCreator extends FilterCreator{
	public FilterMeltingPointTemperatureCreator() {
		parametersComments = new String[] {"min Value:","max Value:"};
		parametersTypes = new ParameterType[] { new DoubleParameter(),new DoubleParameter() };
		parametersValues = new String[] {"50","60"};
	}
	
	@Override public Filter create() {
		Double p0=null;
		Double p1=null;
		try {
			p0 = (Double) parametersTypes[0].parseParameter(parametersValues[0]);
			p1 = (Double) parametersTypes[1].parseParameter(parametersValues[1]);				
		} catch (IncorrectParameterTypeException e) { e.printStackTrace(); } 
		return new FilterMeltingPointTemperature(p0,p1, new SantaluciaTmEstimator()); // TODO use default TME
	}
	@Override public String toString() {
		return "Single Primer - Keep Primers Whose Melting Temperature Is Between ["
			+ this.parametersValues[0]
			+ "°C - "
			+ this.parametersValues[1]
			+ "°C]";
	}
}
